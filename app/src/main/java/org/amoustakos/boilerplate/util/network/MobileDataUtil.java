package org.amoustakos.boilerplate.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import timber.log.Timber;

import static org.amoustakos.boilerplate.util.network.NetworkUtil.getNetworkInfo;


/**
 * NOTE: NEED TO INSTALL AS SYSTEM APP FOR THIS TO WORK.
 */

public class MobileDataUtil {


    /**
     * Checks if the device is connected to a mobile network
     */
    public static boolean isConnected(Context context){
        final ConnectivityManager conManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conManager.getActiveNetworkInfo();

        return net != null
                && net.isConnected()
                && ConnectivityManager.TYPE_MOBILE == net.getType();
    }


	/**
	 * Retrieves the state of the mobile data (on = true, off = false)
	 */
	public static boolean getState(Context context){
        try{
            TelephonyManager telephonyService = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            Method getMobileDataEnabledMethod =
                    telephonyService.getClass().getDeclaredMethod("getDataEnabled");

            if (null != getMobileDataEnabledMethod){
                return (Boolean) getMobileDataEnabledMethod.invoke(telephonyService);
            }
        }catch (Exception e){
            Timber.w(e, e.getMessage());
        }
        return false;
    }


	/**
	 * Sets the state of the mobile data (on = true, off = false)
	 */
	public static boolean setState(Context context, boolean active){
        final ConnectivityManager conman = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Method dataMtd;
        try {
            dataMtd =ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        } catch (NoSuchMethodException e) {
            Timber.w(e, e.getMessage());
            return false;
        }
        dataMtd.setAccessible(true);
        try {
            dataMtd.invoke(conman, active);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Timber.w(e, e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * Checks if there is fast connectivity
     */
    public static boolean isConnectedFast(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isConnected() && isConnectionFast(info.getType(), info.getSubtype());
    }


    /**
     * Checks if the connection is fast
     */
    public static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI)
            return true;
        else if (type == ConnectivityManager.TYPE_MOBILE)
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return true; // ~ 10+ Mbps
                // Unknown
//                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        else
            return false;
    }
}
