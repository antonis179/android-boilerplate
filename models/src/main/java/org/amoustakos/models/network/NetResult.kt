package org.amoustakos.models.network


sealed class NetResult<out T: Any> {
	data class Success<out T: Any>(val data: T) : NetResult<T>()
	data class Error(val exception: Exception) : NetResult<Nothing>()
}

