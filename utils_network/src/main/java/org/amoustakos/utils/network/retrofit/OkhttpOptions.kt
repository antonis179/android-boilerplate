package org.amoustakos.utils.network.retrofit

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Class used to provide arguments to [RetrofitFactory.getHttpClient]
 */
data class OkhttpOptions (
        // Debug
		val logEnabled: Boolean,
		val logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY,

        // Caching
		val cacheSizeMb: Long? = null,
		val cacheDir: File? = null,
		val cacheSubDirectory: String? = null,

        // Connection
		val connectTimeout: Int,
		val readTimeout: Int,
		val writeTimeout: Int,
		val timeUnit: TimeUnit,

        // Interceptors
		val interceptors: List<Interceptor>? = null,
		val networkInterceptors: List<Interceptor>? = null
)
