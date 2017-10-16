package org.amoustakos.deviceutils;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

import timber.log.Timber;

/**
 * TODO
 */
public class MemoryUtil {
	public static final int HIGH = 0,
							MEDIUM = 1,
							LOW = 2,
							UNKNOWN = -1;

	//Thresholds
	private double lowMemThres = 512;
	private double highMemThres = 1536;

	//Dependencies
	private WeakReference<Context> context;

	// =========================================================================================
	// Constructors
	// =========================================================================================

	/**
	 * Constructor that uses the default parameters: <br />
	 * {@link #lowMemThres} <br />
	 * {@link #highMemThres}
	 */
	public MemoryUtil(@NonNull Context context) {
		if (context == null)
			throw new NullPointerException("Context cannot be null");
		this.context = new WeakReference<>(context);
	}

	/**
	 * Constructor that allows setting the threshold parameters <br />
	 * @param lowMemThres (mb): Devices with RAM below this are classified as {@link #LOW} {@link MemoryProfile}
	 * @param highMemThres (mb): Devices with RAM at or over this are classified as {@link #HIGH} {@link MemoryProfile}
	 * <br /> <br />
	 * Devices with RAM capacity between the 2 thresholds are defined as {@link #MEDIUM} {@link MemoryProfile}
	 */
	public MemoryUtil(@NonNull Context context, double lowMemThres, double highMemThres) {
		this(context);
		this.lowMemThres = lowMemThres;
		this.highMemThres = highMemThres;
	}


	// =========================================================================================
	// Functionality
	// =========================================================================================

	/*
	 * Old way of doing this for posterity
	 */
//	public static double calculateRam() {
//		RandomAccessFile reader = null;
//		double totRam = Double.NaN;
//		try {
//			reader = new RandomAccessFile("/proc/meminfo", "r");
//			String line = reader.readLine();
//
//			// Get the Number value from the string
//			Pattern p = Pattern.compile("(\\d+)");
//			Matcher m = p.matcher(line);
//			String value = "";
//			while (m.find())
//				value = m.group(1);
//
//			totRam = Double.parseDouble(value);
//			totRam = totRam / 1024.0;
//		} catch (IOException ex) {
//			Timber.w(ex);
//		} finally {
//			if (reader != null)
//				try {
//					reader.close();
//				} catch (IOException e) {
//					Timber.w(e);
//				}
//		}
//
//		return totRam;
//	}

	@RequiresApi(16)
	public double calculateRam() {
		return calculateRam(context.get());
	}

	@RequiresApi(16)
	public static double calculateRam(Context context) {
		try {
			ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
			actManager.getMemoryInfo(memInfo);
			Long totalMemory = memInfo.totalMem;
			return totalMemory.doubleValue()/1024/1024;
		} catch (Exception ne) {
			Timber.w(ne);
			return Double.NaN;
		}
	}


	// =========================================================================================
	// Profile helpers
	// =========================================================================================

	@MemoryProfile public int profile() {
		double memory;

		memory = calculateRam(context.get());

		if (memory == Double.NaN)
			return UNKNOWN;

		if (memory <= lowMemThres)
			return LOW;
		else if (memory < highMemThres)
			return MEDIUM;
		else
			return HIGH;
	}


	// =========================================================================================
	// Inner classes
	// =========================================================================================

	@Retention(RetentionPolicy.SOURCE)
	@IntDef({HIGH, MEDIUM, LOW, UNKNOWN})
	public @interface MemoryProfile {}
}
