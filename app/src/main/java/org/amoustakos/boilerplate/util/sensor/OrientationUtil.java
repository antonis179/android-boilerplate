package org.amoustakos.boilerplate.util.sensor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static android.content.Context.SENSOR_SERVICE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public final class OrientationUtil implements Application.ActivityLifecycleCallbacks {
	public static final int ORIENTATION_PORTRAIT = ExifInterface.ORIENTATION_ROTATE_90; // 6
	public static final int ORIENTATION_LANDSCAPE_REVERSE = ExifInterface.ORIENTATION_ROTATE_180; // 3
	public static final int ORIENTATION_LANDSCAPE = ExifInterface.ORIENTATION_NORMAL; // 1
	public static final int ORIENTATION_PORTRAIT_REVERSE = ExifInterface.ORIENTATION_ROTATE_270; // 8

	private int smoothness = 1;
	private float averagePitch = 0;
	private float averageRoll = 0;
	private int orientation = ORIENTATION_PORTRAIT;

	private float[] pitches;
	private float[] rolls;

	private SensorManager sManager;


	///////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////

	public OrientationUtil(Context context, @Orientation int defaultOrientation) {
		pitches = new float[smoothness];
		rolls = new float[smoothness];
		orientation = defaultOrientation;
		sManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
	}

	public void registerListeners() {
		//Accelerometer
		if (sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
			sManager.registerListener(
					getEventListener(),
					sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL
			);

		//Magnetometer
		if (sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)
			sManager.registerListener(
					getEventListener(),
					sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
					SensorManager.SENSOR_DELAY_NORMAL
			);
	}

	public void unregisterListeners() {
		sManager.unregisterListener(getEventListener());
	}




	///////////////////////////////////////////////////////////////////////////
	// Calculations
	///////////////////////////////////////////////////////////////////////////

	private synchronized float addValue(float value, float[] values) {
		value = (float) Math.round((Math.toDegrees(value)));
		float average = 0;
		for (int i = 1; i < smoothness; i++) {
			values[i - 1] = values[i];
			average += values[i];
		}
		values[smoothness - 1] = value;
		average = (average + value) / smoothness;
		return average;
	}


	@Orientation
	private int calculateOrientation() {
		// finding local orientation dip
		if (((orientation == ORIENTATION_PORTRAIT || orientation == ORIENTATION_PORTRAIT_REVERSE)
				&& (averageRoll > -30 && averageRoll < 30))) {
			if (averagePitch > 0)
				return ORIENTATION_PORTRAIT_REVERSE;
			else
				return ORIENTATION_PORTRAIT;
		} else {
			// divides between all orientations
			if (Math.abs(averagePitch) >= 30) {
				if (averagePitch > 0)
					return ORIENTATION_PORTRAIT_REVERSE;
				else
					return ORIENTATION_PORTRAIT;
			} else {
				if (averageRoll > 0) {
					return ORIENTATION_LANDSCAPE_REVERSE;
				} else {
					return ORIENTATION_LANDSCAPE;
				}
			}
		}
	}



	///////////////////////////////////////////////////////////////////////////
	// Getters - Setters
	///////////////////////////////////////////////////////////////////////////

	public SensorEventListener getEventListener() {
		return sensorEventListener;
	}

	@Orientation public synchronized int getOrientation() {
		return orientation;
	}


	public float getCameraRotationDegrees() {
		return getCameraRotationDegrees(getOrientation());
	}

	public static float getCameraRotationDegrees(@Orientation int orientation) {
		switch (orientation) {
			case ORIENTATION_LANDSCAPE:
				return -90f;
			case ORIENTATION_PORTRAIT:
				return 0f;
			case ORIENTATION_LANDSCAPE_REVERSE:
				return 90f;
			case ORIENTATION_PORTRAIT_REVERSE:
				return 180f;
			default:
				return Float.MIN_VALUE;
		}
	}


	///////////////////////////////////////////////////////////////////////////
	// Inner classes
	///////////////////////////////////////////////////////////////////////////

	@Retention(SOURCE)
	@IntDef({ORIENTATION_PORTRAIT, ORIENTATION_LANDSCAPE_REVERSE, ORIENTATION_LANDSCAPE, ORIENTATION_PORTRAIT_REVERSE})
	public @interface Orientation {}


	private SensorEventListener sensorEventListener = new SensorEventListener() {
		float[] mGravity;
		float[] mGeomagnetic;


		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
				mGravity = event.values;
			if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
				mGeomagnetic = event.values;
			if (mGravity != null && mGeomagnetic != null) {
				float R[] = new float[9];
				float I[] = new float[9];
				boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
				if (success) {
					float orientationData[] = new float[3];
					SensorManager.getOrientation(R, orientationData);
					averagePitch = addValue(orientationData[1], pitches);
					averageRoll = addValue(orientationData[2], rolls);
					orientation = calculateOrientation();
				}
			}
		}
	};


	///////////////////////////////////////////////////////////////////////////
	// Implemented
	///////////////////////////////////////////////////////////////////////////

	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
	public void onActivityStarted(Activity activity) {}
	public void onActivityStopped(Activity activity) {}
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
	public void onActivityDestroyed(Activity activity) {}

	public void onActivityResumed(Activity activity) {
		registerListeners();
	}

	public void onActivityPaused(Activity activity) {
		unregisterListeners();
	}



}