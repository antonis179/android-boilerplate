package org.amoustakos.boilerplate.io.dao.base

import io.reactivex.Observable
import io.realm.*
import org.amoustakos.utils.io.realm.RealmTraits
import org.amoustakos.utils.io.realm.RealmUtil
import timber.log.Timber

abstract class BaseDao<T : RealmModel> (
        @get:Synchronized protected val realm: Realm,
        private val modelType: Class<T>)
    : RealmTraits() {

    private val finalizer = Observable.fromCallable{realm.close()}
            .doOnError {
                Timber.e(it)
            }!!


    // =========================================================================================
    // Helpers
    // =========================================================================================

    fun has(): Boolean = RealmUtil.has(realm, modelType)

    private val count: Long
        get() = RealmUtil.getCount(realm, modelType)

    fun isAutoUpdate() = realm.isAutoRefresh

    fun isRealmClosed() = realm.isClosed

    fun isValid(model: T) = RealmObject.isValid(model)

    fun isEmpty(): Boolean = count == 0L

    fun sort(field: String, sort: Sort) =
            realm.where(modelType).sort(field, sort).findAll()!!

    // =========================================================================================
    // CRUD - Synced
    // =========================================================================================

    fun trans(body: () -> Unit) = realm trans { body() }

    fun copy(model: T, depth: Int) = RealmUtil.copyFrom(realm, model, depth)

    fun copyAll(depth: Int) = RealmUtil.copyAllFrom(realm, all(), depth)

    fun copyAll(models: Iterable<T>, depth: Int) =
            RealmUtil.copyAllFrom(realm, models, depth)

    fun copyToOrUpdate(model: T) =
            RealmUtil.copyToOrUpdate(realm, model)

    fun copyToOrUpdate(models: Iterable<T>) =
            RealmUtil.copyToOrUpdate(realm, models)

    fun all(): RealmResults<T> = RealmUtil.getByModel(realm, modelType)

    fun add(model: T, copy: Boolean): T? = RealmUtil.add(realm, model, copy)

    fun addOrUpdate(model: T) = RealmUtil.addOrUpdate(realm, model)

    fun remove(model: T) = RealmUtil.remove(realm, model)

    fun remove(models: List<T>) = RealmUtil.remove(realm, models)

    fun clearAll() = RealmUtil.clearAll(realm, modelType)



    fun getOneByColumn(column: String, value: String): T? =
            RealmUtil.getOneByColumn(realm, modelType, column, value)

    fun getOneByColumn(column: String, value: Int): T? =
            RealmUtil.getOneByColumn(realm, modelType, column, value)

    fun getOneByColumn(column: String, value: Double): T? =
            RealmUtil.getOneByColumn(realm, modelType, column, value)

    fun getOneByColumn(column: String, value: Float): T? =
            RealmUtil.getOneByColumn(realm, modelType, column, value)

    fun getOneByColumn(column: String, value: Boolean): T? =
            RealmUtil.getOneByColumn(realm, modelType, column, value)



    fun getByColumn(column: String, value: String): RealmResults<T> =
            RealmUtil.getByColumn(realm, modelType, column, value)

    fun getByColumn(column: String, value: Int): RealmResults<T> =
            RealmUtil.getByColumn(realm, modelType, column, value)

    fun getByColumn(column: String, value: Double): RealmResults<T> =
            RealmUtil.getByColumn(realm, modelType, column, value)

    fun getByColumn(column: String, value: Float): RealmResults<T> =
            RealmUtil.getByColumn(realm, modelType, column, value)

    fun getByColumn(column: String, value: Boolean): RealmResults<T> =
            RealmUtil.getByColumn(realm, modelType, column, value)

    // =========================================================================================
    // Overrides
    // =========================================================================================

    /** This method overrides "finalize" even though it can't be declared in kotlin yet. */
    protected fun finalize() = finalizer.subscribe()!!

}