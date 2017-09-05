package org.amoustakos.boilerplate.util.preferences;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import org.amoustakos.boilerplate.injection.annotations.context.ApplicationContext;

public abstract class SharedPreferencesHelper {

	protected SharedPreferences prefs;


	public SharedPreferencesHelper(@NonNull String fileName, int mode, @ApplicationContext Context con) {
		prefs = con.getSharedPreferences(fileName, mode);
	}

	/**
     * Set a string shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    public void setString(String key, String value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Set a integer shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    public void setInt(String key, int value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Set a Boolean shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    public void setBoolean(String key, boolean value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Get a string shared preference
     * @param key - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public String getString(String key, String defValue){
        return prefs.getString(key, defValue);
    }

    /**
     * Get a integer shared preference
     * @param key - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public int getInt(String key, int defValue){
        return prefs.getInt(key, defValue);
    }

    /**
     * Get a boolean shared preference
     * @param key - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public boolean getBoolean(String key, boolean defValue){
        return prefs.getBoolean(key, defValue);
    }
}
