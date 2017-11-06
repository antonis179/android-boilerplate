package org.amoustakos.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stealthcopter.networktools.Ping;
import com.stealthcopter.networktools.ping.PingResult;

import java.net.UnknownHostException;

import io.reactivex.Observable;

public class NetworkUtil {

	// =========================================================================================
	// Constants
	// =========================================================================================

	private static final String PING_ADDRESS = "8.8.8.8";
	private static final int DEFAULT_TIMEOUT = 1000;
	private static final int DEFAULT_RETRIES = 1;


	// =========================================================================================
	// Basic Android functions
	// =========================================================================================

    /**
     * Checks if the device is connected to a network
     */
    public static boolean isNetworkConnected(@NonNull Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (cm == null)
		    return false;
	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Retrieves the network info
     */
    @Nullable
    public static NetworkInfo getNetworkInfo(@NonNull Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (cm == null)
		    return null;
	    return cm.getActiveNetworkInfo();
    }

    /**
     * Checks if there is any connectivity (this does not mean internet).
     */
    public static boolean isConnected(@NonNull Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isConnected();
    }


	// =========================================================================================
	// Internet connectivity checks
	// =========================================================================================

	/**
	 * Async version of {@link #hasInternetSynchronous(String, int, int)}. <br />
	 * Note that the {@link Observable} returned does not implement error handling.
	 */
	public static Observable<Boolean> hasInternetAsync(
			@NonNull String address,
			int timeoutMillis,
			int repeatPing
	) throws UnknownHostException {

		return Observable.just(hasInternetSynchronous(address, timeoutMillis, repeatPing));
	}

	/**
	 * Async version of {@link #hasInternetSynchronous()}. <br />
	 * Note that the {@link Observable} returned does not implement error handling.
	 */
	public static Observable<Boolean> hasInternetAsync() throws UnknownHostException {
		return Observable.just(hasInternetSynchronous());
	}

	/**
	 * Synchronous method that checks for internet availability by executing <br />
	 * a ping command on the Google DNS server. <br />
	 *
	 * @return true if there is internet connectivity - false otherwise.
	 * @throws UnknownHostException if the address input was incorrect or if the host was not found.
	 * @see #hasInternetSynchronous(String, int, int)
	 */
	public static boolean hasInternetSynchronous() throws UnknownHostException {
		return hasInternetSynchronous(PING_ADDRESS, DEFAULT_TIMEOUT, DEFAULT_RETRIES);
	}

	/**
	 * Synchronous method that checks for internet availability by executing a ping command.
	 *
	 * @param address       The address to ping.
	 * @param timeoutMillis The timeout to specify for the execution of the command.
	 * @param repeatPing    The times to retry execution.
	 * @return true if there is internet connectivity - false otherwise.
	 * @throws UnknownHostException if the address input was incorrect or if the host was not found.
	 */
	public static boolean hasInternetSynchronous(@NonNull String address, int timeoutMillis, int repeatPing) throws UnknownHostException {
		PingResult pingResult = Ping.onAddress(address)
				.setTimeOutMillis(timeoutMillis)
				.setTimes(repeatPing)
				.doPing();

		return !pingResult.hasError();
	}



}