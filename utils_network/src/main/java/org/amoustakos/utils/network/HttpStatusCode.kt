package org.amoustakos.utils.network

import retrofit2.HttpException
import java.net.HttpURLConnection

/**
 * Constants enumerating the HTTP status codes.
 */
object HttpStatusCode {

    /**
     * Status code for a successful request.
     */
    val OK = 200
    /**
     * Status code for a resource corresponding to any one of a set of
     * representations.
     */
    val MULTIPLE_CHOICES = 300
    /**
     * Status code for a request that requires user authentication.
     */
    val UNAUTHORIZED = 401
    /**
     * Status code for a server that understood the request, but is refusing to
     * fulfill it.
     */
    val FORBIDDEN = 403
    /**
     * Status code for a server that has not found anything matching the
     * Request-URI.
     */
    val NOT_FOUND = 404
    /**
     * Status code for a server that detects an error in the request such as
     * missing or inconsistent parameters
     */
    val UNPROCESSABLE_ENTITY = 422
    /**
     * Status code for an internal server error. For example exception is thrown
     * at the back-end.
     */
    val SERVER_ERROR = 500
    /**
     * Status code for a bad gateway.
     */
    val BAD_GATEWAY = 502
    /**
     * Status code for a service that is unavailable on the server.
     */
    val SERVICE_UNAVAILABLE = 503


    /**
     * Returns whether the given HTTP response status code is a success code
     * `>= 200 and < 300`.
     */
    fun isSuccess(statusCode: Int): Boolean {
        return statusCode >= OK && statusCode < MULTIPLE_CHOICES
    }

    /**
     * Returns whether the given HTTP response status code is a redirection code.
     */
    fun isRedirection(statusCode: Int): Boolean {
        return (statusCode == HttpURLConnection.HTTP_MOVED_TEMP
                || statusCode == HttpURLConnection.HTTP_MOVED_PERM
                || statusCode == HttpURLConnection.HTTP_SEE_OTHER)
    }

    /**
     * Returns true if the Throwable is an instance of RetrofitError
     */
    fun isHttpStatusCode(throwable: Throwable): Boolean {
        return throwable is HttpException
    }

    /**
     * Checks whether the [HttpException]'s code is equal to the provided one.
     */
    fun isEqualCode(throwable: HttpException, statusCode: Int): Boolean {
        return throwable.code() == statusCode
    }

    /**
     * Checks whether the provided [Throwable] is an [HttpException] <br></br>
     * and its code equals to [.UNAUTHORIZED]
     */
    fun isUnauthorized(throwable: Throwable): Boolean {
        return isHttpStatusCode(throwable) && isEqualCode(throwable as HttpException, HttpStatusCode.UNAUTHORIZED)
    }
}