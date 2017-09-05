package org.amoustakos.boilerplate.util.io;


import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;



/**
 * Utility class to help with Realm IO. <br />
 * <br />
 * Most of these methods can be implemented with {@link RealmObject} instead of {@link RealmModel} <br />
 * but this would require you to extend this at model level instead of implement.
 * <br />
 * <br />
 */
public final class RealmUtil {


	/*
	 * Helpers
	 */

	public static boolean has(@NonNull Realm realm, Class<? extends RealmModel> model) {
		return realm.where(model).count() > 0;
	}

	public static Long getCount(@NonNull Realm realm, Class<? extends RealmModel> model) {
		return realm.where(model).count();
	}


	/*
	 * CRUD
	 */

	public static RealmModel add(@NonNull Realm realm, @NonNull RealmModel model, boolean copy) {
		realm.beginTransaction();
		RealmModel obj = null;
		if(copy)
			obj = realm.copyToRealm(model);
		else
			realm.insert(model);
		realm.commitTransaction();
		return obj;
	}


	public static void addOrUpdate(@NonNull Realm realm, @NonNull RealmModel model){
		realm.beginTransaction();
		realm.insertOrUpdate(model);
		realm.commitTransaction();
	}


	public static void remove(@NonNull Realm realm, @NonNull RealmModel model){
		realm.beginTransaction();
		RealmObject.deleteFromRealm(model);
		realm.commitTransaction();
	}


	public static void clearAll(@NonNull Realm realm, @NonNull Class<? extends RealmModel> model) {
		realm.beginTransaction();
		realm.delete(model);
		realm.commitTransaction();
	}


	public static RealmResults getByModel(@NonNull Realm realm, @NonNull Class model) {
		return realm.where(model).findAll();
	}


	public static RealmModel getByColumn(@NonNull Realm realm, Class model, String column, String value) {
		return realm.where(model).equalTo(column, value).findFirst();
	}


	public static RealmModel getByColumn(@NonNull Realm realm, Class model, String column, int value) {
		return realm.where(model).equalTo(column, value).findFirst();
	}


	public static RealmModel getByColumn(@NonNull Realm realm, Class model, String column, double value) {
		return realm.where(model).equalTo(column, value).findFirst();
	}


	public static RealmModel getByColumn(@NonNull Realm realm, Class model, String column, float value) {
		return realm.where(model).equalTo(column, value).findFirst();
	}





}
