package org.amoustakos.boilerplate.util.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.inputmethod.InputMethodManager;

import org.amoustakos.boilerplate.injection.ActivityContext;


public final class ViewUtil {

	/**
	 * Translates pixels to dp
	 */
    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }


	/**
	 * Translates dp to pixels
	 */
	public static int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }


	/**
	 * Hide the keyboard
	 */
	public static void hideKeyboard(@ActivityContext Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

}
