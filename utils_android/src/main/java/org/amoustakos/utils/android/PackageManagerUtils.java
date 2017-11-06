package org.amoustakos.utils.android;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


/**
 * Utility class that provides helper methods for {@link PackageManager}.
 */
public final class PackageManagerUtils {

	/**
	 * Retrieve activities defined in the Manifest.
	 *
	 * @param context     Needed to get a {@link PackageManager} instance.
	 * @param basePackage Required to specify the manifest to look through.
	 * @param <T>         Defines the casting performed to the activities retrieved.
	 */
	public static <T extends Activity> List<Class<? extends T>> getDefinedActivities(
			@NonNull Context context,
			@NonNull String basePackage
	) {
		PackageManager pm = context.getPackageManager();
		return getDefinedActivities(pm, basePackage);
	}

	/**
	 * Retrieve activities defined in the Manifest.
	 *
	 * @param pm          The {@link PackageManager} instance to be used.
	 * @param basePackage Required to specify the manifest to look through.
	 * @param <T>         Defines the casting performed to the activities retrieved.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Activity> List<Class<? extends T>> getDefinedActivities(
			@NonNull PackageManager pm,
			@NonNull String basePackage
	) {
		List<Class<? extends T>> activities = new ArrayList<>();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(basePackage, PackageManager.GET_ACTIVITIES);
			ActivityInfo[] act = packageInfo.activities;
			for (ActivityInfo ai : act) {
				try {
					if (!ai.packageName.equals(basePackage)) continue;
					Class<? extends T> cl = (Class<? extends T>) Class.forName(ai.name);
					activities.add(cl);
				} catch (ClassNotFoundException | ClassCastException cle) {
					Timber.v(cle);
				}
			}
		} catch (PackageManager.NameNotFoundException e) {
			Timber.e(e);
		}
		return activities;
	}


}
