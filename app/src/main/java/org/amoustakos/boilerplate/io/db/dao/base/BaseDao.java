package org.amoustakos.boilerplate.io.db.dao.base;


import android.support.annotation.NonNull;

import org.amoustakos.boilerplate.util.io.RealmUtil;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;


/**
 * TODO: WiP
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class BaseDao<T extends RealmModel> {
	private final Class<T> modelType;

	private Realm realm;



	/* =============================================================================================
	 * Constructor
	 * ===========================================================================================*/

	public BaseDao(Realm realm, Class<T> modelType) {
		this.realm = realm;
		this.modelType = modelType;
	}




	/* =============================================================================================
	 * Helpers
	 * ===========================================================================================*/

	protected boolean has() {
		return RealmUtil.has(getRealm(), modelType);
	}

	protected Long getCount() {
		return RealmUtil.getCount(getRealm(), modelType);
	}



	/* =============================================================================================
	 * CRUD
	 * ===========================================================================================*/

	public T add(@NonNull T model, boolean copy) {
		return (T) RealmUtil.add(getRealm(), model, copy);
	}

	public void addOrUpdate(@NonNull T model){
		RealmUtil.addOrUpdate(getRealm(), model);
	}

	public void remove(@NonNull T model){
		RealmUtil.remove(getRealm(), model);
	}

	public void clearAll() {
		RealmUtil.clearAll(getRealm(), modelType);
	}



	public RealmResults<T> getAll() {
		return RealmUtil.getByModel(getRealm(), modelType);
	}



	public T getOneByColumn(String column, String value) {
		return (T) RealmUtil.getOneByColumn(getRealm(), modelType, column, value);
	}

	public T getOneByColumn(String column, int value) {
		return (T) RealmUtil.getOneByColumn(getRealm(), modelType, column, value);
	}

	public T getOneByColumn(String column, double value) {
		return (T) RealmUtil.getOneByColumn(getRealm(), modelType, column, value);
	}

	public T getOneByColumn(String column, float value) {
		return (T) RealmUtil.getOneByColumn(getRealm(), modelType, column, value);
	}



	public RealmResults<T> getByColumn(String column, String value) {
		return (RealmResults<T>) RealmUtil.getByColumn(getRealm(), modelType, column, value);
	}

	public RealmResults<T> getByColumn(String column, int value) {
		return (RealmResults<T>) RealmUtil.getByColumn(getRealm(), modelType, column, value);
	}

	public RealmResults<T> getByColumn(String column, double value) {
		return (RealmResults<T>) RealmUtil.getByColumn(getRealm(), modelType, column, value);
	}

	public RealmResults<T> getByColumn(String column, float value) {
		return (RealmResults<T>) RealmUtil.getByColumn(getRealm(), modelType, column, value);
	}




	/* =============================================================================================
	 * Overrides
	 * ===========================================================================================*/

	@Override
	protected void finalize() throws Throwable {
		getRealm().close();
		super.finalize();
	}



	/* =============================================================================================
	 * Getters - Setters
	 * ===========================================================================================*/

	protected synchronized Realm getRealm() {return realm;}
}
