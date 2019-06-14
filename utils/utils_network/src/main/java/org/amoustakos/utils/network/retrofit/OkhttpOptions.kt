package org.amoustakos.utils.network.retrofit

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Class used to provide arguments to [RetrofitFactory.getHttpClient]
 */
data class OkhttpOptions (
		val logging: OkHttpLogging,
		val cache: OkHttpCache? = null,
		val authentication: OkHttpAuthentication? = null,
		val connection: OkHttpConnection? = null,
		val events: OkHttpEvents? = null,
		val typedInterceptors: List<TypedInterceptor>? = null
)






data class OkHttpLogging(
		val logEnabled: Boolean,
		val logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
)

data class OkHttpCache(
		val cacheSizeMb: Long,
		val cacheDir: File,
		val cacheSubDirectory: String
)

data class OkHttpAuthentication(
		val authenticator: Authenticator? = null,
		val certificatePinner: CertificatePinner? = null
)

data class OkHttpConnection(
		val connectTimeout: Long? = null,
		val readTimeout: Long? = null,
		val writeTimeout: Long? = null,
		val callTimeout: Long? = null,
		val timeUnit: TimeUnit? = null,
		val connectionSpecs: List<ConnectionSpec>? = null,
		val dispatcher: Dispatcher? = null,
		val cookieJar: CookieJar? = null,
		val connectionPool: ConnectionPool? = null,
		val dns: Dns? = null
)

data class OkHttpEvents(
		val eventListener: EventListener? = null,
		val eventListenerFactory: EventListener.Factory? = null
)

enum class InterceptorType {
	INTERCEPTOR, NETWORK_INTERCEPTOR
}

data class TypedInterceptor(
		val type: InterceptorType,
		val interceptor: Interceptor
)