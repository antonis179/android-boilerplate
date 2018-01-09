package org.amoustakos.models.network


data class NetEvent<out T>(
        val error: Int? = null,
        val hasError: Boolean = false,
        val errorMessage: String,
        val item: T? = null)
