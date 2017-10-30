package org.amoustakos.utils.network;

import java.net.HttpURLConnection;

import retrofit2.HttpException;

/**
 * Constants enumerating the HTTP status codes.
 */
public final class HttpStatusCode {

    public static final int
    /**
     * Status code for a successful request.
     */
    OK = 200,
    /**
     * Status code for a resource corresponding to any one of a set of
     * representations.
     */
    MULTIPLE_CHOICES = 300,
    /**
     * Status code for a request that requires user authentication.
     */
    UNAUTHORIZED = 401,
    /**
     * Status code for a server that understood the request, but is refusing to
     * fulfill it.
     */
    FORBIDDEN = 403,
    /**
     * Status code for a server that has not found anything matching the
     * Request-URI.
     */
    NOT_FOUND = 404,
    /**
     * Status code for a server that detects an error in the request such as
     * missing or inconsistent parameters
     */
    UNPROCESSABLE_ENTITY = 422,
    /**
     * Status code for an internal server error. For example exception is thrown
     * at the back-end.
     */
    SERVER_ERROR = 500,
    /**
     * Status code for a bad gateway.
     */
    BAD_GATEWAY = 502,
    /**
     * Status code for a service that is unavailable on the server.
     */
    SERVICE_UNAVAILABLE = 503;


    /**
     * Returns whether the given HTTP response status code is a success code
     * {@code >= 200 and < 300}.
     */
    public static boolean isSuccess(int statusCode) {
        return statusCode >= OK && statusCode < MULTIPLE_CHOICES;
    }

    /**
     * Returns whether the given HTTP response status code is a redirection code.
     */
    public static boolean isRedirection(int statusCode) {
        return statusCode == HttpURLConnection.HTTP_MOVED_TEMP
                || statusCode == HttpURLConnection.HTTP_MOVED_PERM
                || statusCode == HttpURLConnection.HTTP_SEE_OTHER;
    }

    /**
     * Returns true if the Throwable is an instance of RetrofitError
     */
    public static boolean isHttpStatusCode(Throwable throwable) {
        return throwable instanceof HttpException;
    }

	/**
	 * Checks whether the {@link HttpException}'s code is equal to the provided one.
	 */
	public static boolean isEqualCode(HttpException throwable, int statusCode) {
        return throwable.code() == statusCode;
    }

	/**
	 * Checks whether the provided {@link Throwable} is an {@link HttpException} <br />
	 * and its code equals to {@link #UNAUTHORIZED}
	 */
	public static boolean isUnauthorized(Throwable throwable) {
        return isHttpStatusCode(throwable)
                && isEqualCode((HttpException) throwable, HttpStatusCode.UNAUTHORIZED);
    }
}