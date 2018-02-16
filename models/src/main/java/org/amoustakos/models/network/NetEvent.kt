package org.amoustakos.models.network


data class NetEvent<out T> @JvmOverloads constructor(

        val error: Int? = null,
        val hasError: Boolean = true,
        val errorMessage: String? = null,
        val item: T? = null

)


