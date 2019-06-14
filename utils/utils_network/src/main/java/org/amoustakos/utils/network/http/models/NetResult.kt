package org.amoustakos.utils.network.http.models


sealed class NetResult<out T: Any> {
	data class Success<out T: Any>(val data: T) : NetResult<T>()
	data class Error(val exception: Throwable) : NetResult<Nothing>()
	object Cancelled : NetResult<Nothing>()
}

