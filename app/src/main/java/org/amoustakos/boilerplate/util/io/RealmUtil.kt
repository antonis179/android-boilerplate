package org.amoustakos.boilerplate.util.io

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmResults

/**
 * Utility class to help with Realm IO. <br></br>
 * <br></br>
 * Most of these methods can be implemented with [RealmObject] instead of [RealmModel] <br></br>
 * but this would require you to extend this at model level instead of implement.
 * <br></br>
 * <br></br>
 */
@Suppress("UNCHECKED_CAST")
object RealmUtil {


    // =========================================================================================
    // Helpers
    // =========================================================================================

    fun has(realm: Realm, model: Class<out RealmModel>): Boolean = realm.where(model).count() > 0

    fun getCount(realm: Realm, model: Class<out RealmModel>): Long = realm.where(model).count()


    infix fun Realm.transWithResp(transaction: () -> RealmModel?): RealmModel? {
        beginTransaction()
        val resp = transaction.invoke()
        commitTransaction()
        return resp
    }

    infix fun Realm.trans(transaction: () -> Unit) {
        this.beginTransaction()
        transaction.invoke()
        this.commitTransaction()
    }


    // =========================================================================================
    // CRUD
    // =========================================================================================

    fun <T : RealmModel> add(realm: Realm, model: T, copy: Boolean): T? {
       return (realm transWithResp {
           var obj: RealmModel? = null
           if (copy)
               obj = realm.copyToRealm(model)
           else
               realm.insert(model)
           obj
       }) as T?
    }


    fun addOrUpdate(realm: Realm, model: RealmModel) = realm trans {
            realm.insertOrUpdate(model)
    }

    fun remove(realm: Realm, model: RealmModel) = realm trans {
            RealmObject.deleteFromRealm(model)
    }

    fun clearAll(realm: Realm, model: Class<out RealmModel>) = realm trans {
        realm.delete(model)
    }


    fun <T : RealmModel> getByModel(realm: Realm, model: Class<T>): RealmResults<T> =
            realm.where(model).findAll()


    fun <T : RealmModel> getOneByColumn(realm: Realm, model: Class<T>, column: String, value: String): T? =
            realm.where(model).equalTo(column, value).findFirst()

    fun <T : RealmModel> getOneByColumn(realm: Realm, model: Class<T>, column: String, value: Int): T? =
            realm.where(model).equalTo(column, value).findFirst()

    fun <T : RealmModel> getOneByColumn(realm: Realm, model: Class<T>, column: String, value: Double): T? =
            realm.where(model).equalTo(column, value).findFirst()

    fun <T : RealmModel> getOneByColumn(realm: Realm, model: Class<T>, column: String, value: Float): T? =
            realm.where(model).equalTo(column, value).findFirst()


    fun <T : RealmModel> getByColumn(realm: Realm, model: Class<T>, column: String, value: String): RealmResults<T> =
            realm.where(model).equalTo(column, value).findAll()

    fun <T : RealmModel> getByColumn(realm: Realm, model: Class<T>, column: String, value: Int): RealmResults<T> =
            realm.where(model).equalTo(column, value).findAll()

    fun <T : RealmModel> getByColumn(realm: Realm, model: Class<T>, column: String, value: Double): RealmResults<T> =
            realm.where(model).equalTo(column, value).findAll()

    fun <T : RealmModel> getByColumn(realm: Realm, model: Class<T>, column: String, value: Float): RealmResults<T> =
            realm.where(model).equalTo(column, value).findAll()


}