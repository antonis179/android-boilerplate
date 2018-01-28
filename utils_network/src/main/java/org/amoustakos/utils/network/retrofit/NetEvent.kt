package org.amoustakos.utils.network.retrofit


class NetEvent<out T> {

    val error: Int?
    val hasError: Boolean
    val errorMessage: String?
    val item: T?

    constructor(error: Int, hasError: Boolean, item: T, errorMessage: String) {
        this.error = error
        this.hasError = hasError
        this.item = item
        this.errorMessage = errorMessage
    }

    constructor(item: T) {
        this.item = item
        this.error = null
        this.hasError = false
        this.errorMessage = null
    }

    constructor(error: Int?, hasError: Boolean, errorMessage: String?) {
        this.item = null
        this.error = error
        this.hasError = hasError
        this.errorMessage = errorMessage
    }

    constructor(error: Int):        this(error, true, null)
    constructor(hasError: Boolean): this(null, hasError, null)
}
