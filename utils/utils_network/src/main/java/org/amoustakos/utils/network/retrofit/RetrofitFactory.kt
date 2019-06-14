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

	    opts.validateEagerly?.let   { builder.validateEagerly(it) }
	    opts.callbackExecutor?.let  { builder.callbackExecutor(it) }

        return builder.build()
    }


    @JvmStatic
    fun <T> newService(engine: Retrofit, service: Class<T>): T = engine.create(service)


    @JvmStatic
    fun getHttpClient(opts: OkhttpOptions): OkHttpClient {
        val client = OkHttpClient.Builder()

	    opts.authentication?.let {
		    client.configureAuthentication(it)
	    }
	    opts.cache?.let {
		    client.configureCache(it)
	    }
	    opts.events?.let {
		    client.configureEvents(it)
	    }
	    opts.connection?.let {
		    client.configureConnection(it)
	    }
	    client.configureInterceptors(opts)
	    client.configureLogging(opts.logging)

        return client.build()
    }




	// =========================================================================================
	// Internal helpers
	// =========================================================================================

	private fun OkHttpClient.Builder.configureAuthentication(opts: OkHttpAuthentication) {
		opts.authenticator?.let { authenticator(it) }
		opts.certificatePinner?.let { certificatePinner(it) }
	}

	private fun OkHttpClient.Builder.configureCache(opts: OkHttpCache) {
		val cacheOptionsValid = opts.cacheSizeMb > 0L

		if (cacheOptionsValid) {
			val cacheSize: Long = opts.cacheSizeMb
			val cache = Cache(
					File(opts.cacheDir, opts.cacheSubDirectory),
					cacheSize
			)
			cache(cache)
		}
	}

	private fun OkHttpClient.Builder.configureEvents(opts: OkHttpEvents) {
		opts.eventListenerFactory?.let { eventListenerFactory(it) }
		opts.eventListener?.let { eventListener(it) }
	}

	private fun OkHttpClient.Builder.configureConnection(opts: OkHttpConnection) {
		opts.connectionSpecs?.let { connectionSpecs(it) }
		opts.connectionPool?.let { connectionPool(it) }
		opts.dns?.let { dns(it) }
		opts.dispatcher?.let { dispatcher(it) }
		opts.cookieJar?.let { cookieJar(it) }

		opts.timeUnit?.let { timeUnit ->
			opts.callTimeout?.let { timeout -> callTimeout(timeout, timeUnit) }
			opts.connectTimeout?.let { timeout -> connectTimeout(timeout, timeUnit) }
			opts.readTimeout?.let { timeout -> readTimeout(timeout, timeUnit) }
			opts.writeTimeout?.let { timeout -> writeTimeout(timeout, timeUnit) }
		}
	}

	private fun OkHttpClient.Builder.configureInterceptors(opts: OkhttpOptions) {
		opts.typedInterceptors?.forEach {
			when (it.type) {
				InterceptorType.INTERCEPTOR         -> addInterceptor(it.interceptor)
				InterceptorType.NETWORK_INTERCEPTOR -> addNetworkInterceptor(it.interceptor)
			}
		}
	}

	private fun OkHttpClient.Builder.configureLogging(opts: OkHttpLogging) {
		val loggingInterceptor = HttpLoggingInterceptor()
		if (opts.logEnabled)
			loggingInterceptor.level = opts.logLevel
		else // every time you log in production a puppy dies.
			loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

		addInterceptor(loggingInterceptor)
	}

}
