package org.amoustakos.boilerplate.io.local.dao.base

import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmResults
import org.amoustakos.boilerplate.util.io.RealmTraits
import org.amoustakos.boilerplate.util.io.RealmUtil
import org.amoustakos.utils.android.RxUtil

abstract class BaseDao<T : RealmModel> (
        @get:Synchronized protected val realm: Realm,
        private val modelType: Class<T>)
    : RealmTraits() {


    // =========================================================================================
    // Helpers
    // =========================================================================================

    fun has(): Boolean = RealmUtil.has(realm, modelType)

    val count: Long
        get() = RealmUtil.getCount(realm, modelType)

    fun isAutoUpdate(): Boolean = realm.isAutoRefresh

    fun isRealmClosed(): Boolean = realm.isClosed

    fun isValid(model: T): Boolean = RealmObject.isValid(model)

    fun isEmpty(): Boolean = count == 0L

    // =========================================================================================
    // CRUD
    // =========================================================================================

    fun all(): RealmResults<T> = RealmUtil.getByModel(realm, modelType)

    fun add(model: T, copy: Boolean): T? = RealmUtil.add(realm, model, copy)

    fun addOrUpdate(model: T) = RealmUtil.addOrUpdate(realm, model)

    fun remove(model: T) = RealmUtil.remove(realm, model)

    /** Max 100 objects (Realm limitation per transaction) **/
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

    fun getByColumn(column: String, value: String): RealmResults<T> =
            RealmUtil.getByColumn(realm, modelType, column, value)

    fun getByColumn(column: String, value: Int): RealmResults<T> =
            RealmUtil.getByColumn(realm, modelType, column, value)

    fun getByColumn(column: String, value: Double): RealmResults<T> =
            RealmUtil.getByColumn(realm, modelType, column, value)

    fun getByColumn(column: String, value: Float): RealmResults<T> =
            RealmUtil.getByColumn(realm, modelType, column, value)

    // =========================================================================================
    // Overrides
    // =========================================================================================

    /** This method overrides "finalize" even though it can't be declared in kotlin yet. */
    protected fun finalize() =
            Observable.fromCallable{realm.close()}
                    .compose(RxUtil.applyForegroundSchedulers())
                    .subscribe()!!

}