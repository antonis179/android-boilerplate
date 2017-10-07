package org.amoustakos.boilerplate.util;

import android.support.annotation.Nullable;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


@SuppressWarnings("unchecked")
public class ReflectionUtil {

	private static final char PKG_SEPARATOR = '.';
	private static final char DIR_SEPARATOR = '/';
	private static final String CLASS_FILE_SUFFIX = ".class";
	private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";


	// =========================================================================================
	// Class Reflections
	// =========================================================================================

	/**
	 * Scans the provided package for the classes that match the infered type.
	 */
	public static <T> List<Class<T>> listClasses(String basePackage) {
		String scannedPath = basePackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
		URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
		if (scannedUrl == null)
			throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, basePackage));


		File scannedDir = new File(scannedUrl.getFile());
		List<Class<T>> classes = new ArrayList<>();
		for (File file : scannedDir.listFiles()) {
			try {
				classes.addAll(listClasses(file, basePackage));
			} catch(ClassCastException ce) {
				Timber.d(ce);
			}
		}
		return classes;
	}

	/**
	 * Scans a {@link File} for the classes that match the inferred type. <br />
	 * If the file is under another package you will need to provide the parent package as well (null otherwise).
	 */
	public static <T> List<Class<T>> listClasses(File file, @Nullable String basePackage) {
		List<Class<T>> classes = new ArrayList<>();
		String resource;

		if (basePackage != null)
			resource = basePackage + PKG_SEPARATOR + file.getName();
		else
			resource = file.getName();

		if (file.isDirectory())
			for (File child : file.listFiles())
				classes.addAll(listClasses(child, resource));

		else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
			int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
			String className = resource.substring(0, endIndex);
			try {
				Class<T> item = (Class<T>) Class.forName(className);
				classes.add(item);
			} catch (ClassNotFoundException | ClassCastException ce) {
				Timber.d(ce);
			}
		}
		return classes;
	}










}
