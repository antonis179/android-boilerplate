package org.amoustakos.utils.network.retrofit

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File


object RetrofitFactory {


    @JvmStatic
    fun getRetrofitEngine(okHttpClient: OkHttpClient, opts: RetrofitEngineOptions): Retrofit {
        val builder = Retrofit.Builder()
                .baseUrl(opts.baseUrl)
                .client(okHttpClient)

        opts.adapterFactories
		        .forEach { builder.addCallAdapterFactory(it) }
        opts.converterFactories
		        .forEach { builder.addConverterFactory(it) }

        return builder.build()
    }


    @JvmStatic
    fun <T> newService(engine: Retrofit, service: Class<T>): T = engine.create(service)


    @JvmStatic
    fun getHttpClient(opts: OkhttpOptions): OkHttpClient {
        val client = OkHttpClient.Builder()

        // Cache
	    val isCacheSizeValid =
			    opts.cacheSizeMb?.let { it > 0 } == true

	    if (isCacheSizeValid && (opts.cacheDir == null || opts.cacheSubDirectory == null))
		    throw IllegalArgumentException("You need to provide a directory to enable caching")
	    else {
	        val cacheSize: Long = opts.cacheSizeMb
			        ?: throw NullPointerException("cache size was null")
            val cache = Cache(
		            File(opts.cacheDir, opts.cacheSubDirectory),
		            cacheSize
            )
            client.cache(cache)
        }

        // Connection
        client.connectTimeout(opts.connectTimeout.toLong(), opts.timeUnit)
        client.readTimeout(opts.readTimeout.toLong(), opts.timeUnit)
        client.writeTimeout(opts.writeTimeout.toLong(), opts.timeUnit)

        // Logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor()
        if (opts.logEnabled)
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else // every time you log in production a puppy dies.
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

        client.addInterceptor(loggingInterceptor)

        // Provided interceptors
        opts.interceptors?.forEach { client.addInterceptor(it) }

        return client.build()
    }

}
