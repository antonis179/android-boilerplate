package org.amoustakos.boilerplate.util;


public final class ErrorUtil {


    /**
     * Checks if the {@link Throwable} is a connection error.
     */
    public static boolean isConnectionError(Throwable t){
        return t instanceof java.net.ConnectException
                || t instanceof java.net.NoRouteToHostException
                || t instanceof java.net.PortUnreachableException
                || t instanceof java.net.ProtocolException
                || t instanceof java.net.SocketException
                || t instanceof java.net.SocketTimeoutException
                || t instanceof java.net.UnknownHostException
                || t instanceof java.net.UnknownServiceException;
    }





}
