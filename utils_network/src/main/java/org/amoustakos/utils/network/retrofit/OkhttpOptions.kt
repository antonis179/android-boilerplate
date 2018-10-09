package org.amoustakos.utils.network.retrofit

import okhttp3.Interceptor
import java.io.File
import java.util.concurrent.TimeUnit

data class OkhttpOptions (
        // Debug
		val logEnabled: Boolean,

        // Caching
		val cacheSizeMb: Long?,
		val cacheDir: File?,
		val cacheSubDirectory: String?,

        // Connection
		val connectTimeout: Int,
		val readTimeout: Int,
		val writeTimeout: Int,
		val timeUnit: TimeUnit,

        // Interceptors
		val interceptors: List<Interceptor>?
)
