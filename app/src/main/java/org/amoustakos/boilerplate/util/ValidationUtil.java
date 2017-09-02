package org.amoustakos.boilerplate.util;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import timber.log.Timber;


public final class ValidationUtil {



    public static boolean isValidEmail(CharSequence target) {
        try {
            return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }catch(Exception e){
            Timber.d(e);
            return false;
        }
    }


    public static boolean isValidURL(String url){
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            Timber.d(e);
            return false;
        }
    }


    public static boolean isValidIPv4(String ip){
        try{
            Pattern ipPattern = Pattern.compile(
                    "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
            return ipPattern.matcher(ip).matches();
        } catch (Exception e){
            Timber.d(e);
            return false;
        }
    }


    public static boolean isValidPort(String port){
        try{
            int fPort = Integer.parseInt(port);
            return fPort>=1 && fPort<=65535;
        }catch(Exception e){
            Timber.d(e);
            return false;
        }
    }

}
