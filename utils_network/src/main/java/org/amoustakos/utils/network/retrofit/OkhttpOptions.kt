package org.amoustakos.utils.network.retrofit

import okhttp3.*
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

		// Authentication
		val authenticator: Authenticator? = null,
		val certificatePinner: CertificatePinner? = null,

		// DNS
		val dns: Dns? = null,

        // Connection options
		val connectTimeout: Long? = null,
		val readTimeout: Long? = null,
		val writeTimeout: Long? = null,
		val callTimeout: Long? = null,
		val timeUnit: TimeUnit? = null,
		val connectionSpecs: List<ConnectionSpec>? = null,
		val dispatcher: Dispatcher? = null,
		val cookieJar: CookieJar? = null,
		val connectionPool: ConnectionPool? = null,

		// Events
		val eventListener: EventListener? = null,
		val eventListenerFactory: EventListener.Factory? = null,

        // Interceptors
		val interceptors: List<InterceptorWithType>? = null
)


enum class InterceptorType {
	INTERCEPTOR, NETWORK_INTERCEPTOR
}

data class InterceptorWithType(
		val type: InterceptorType,
		val interceptor: Interceptor
)