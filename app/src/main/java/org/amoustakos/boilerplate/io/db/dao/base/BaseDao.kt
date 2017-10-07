package org.amoustakos.boilerplate.io.db.dao.base

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import org.amoustakos.boilerplate.util.io.RealmUtil

abstract class BaseDao<T : RealmModel> (
                                            @get:Synchronized protected val realm: Realm,
                                            private val modelType: Class<T>
                                        ) {


    // =========================================================================================
    // Helpers
    // =========================================================================================

    protected fun has(): Boolean = RealmUtil.has(realm, modelType)

    protected val count: Long?
        get() = RealmUtil.getCount(realm, modelType)


    // =========================================================================================
    // CRUD
    // =========================================================================================

    val all: RealmResults<T>
        get() = RealmUtil.getByModel(realm, modelType)

    fun add(model: T, copy: Boolean): T? = RealmUtil.add(realm, model, copy)

    fun addOrUpdate(model: T) {
        RealmUtil.addOrUpdate(realm, model)
    }

    fun remove(model: T) {
        RealmUtil.remove(realm, model)
    }

    fun clearAll() {
        RealmUtil.clearAll(realm, modelType)
    }


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
    protected fun finalize() = realm.close()
}