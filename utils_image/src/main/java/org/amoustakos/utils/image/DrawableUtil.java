package org.amoustakos.utils.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;


public final class DrawableUtil {

	public static Drawable getDrawable(Context con, @DrawableRes int res) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			return con.getDrawable(res);
		} else {
			return con.getResources().getDrawable(res, con.getTheme());
		}
	}

}
