package org.amoustakos.boilerplate.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;


public final class WifiUtil {

	/**
	 * Checks if the device is connected to a WiFi network
	 */
    public static boolean isConnected(Context context){
        final ConnectivityManager conManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conManager.getActiveNetworkInfo();

        if(net != null
                && net.isConnected()
                && ConnectivityManager.TYPE_WIFI == net.getType())
            return true;
        else
            return false;
    }

    /**
     * Sets the state of the WiFi antenna (on = true, off = false) <br />
     * USES: android.permission.CHANGE_WIFI_STATE
     */
    public static boolean setState(Context context, boolean active){
        final WifiManager wifiManager = (WifiManager)
                context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.setWifiEnabled(active);
    }

	/**
	 * Returns the state of the WiFi antenna (on = true, off = false)
	 */
	public static boolean getState(Context context){
        final WifiManager wifiManager = (WifiManager)
                context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

}
