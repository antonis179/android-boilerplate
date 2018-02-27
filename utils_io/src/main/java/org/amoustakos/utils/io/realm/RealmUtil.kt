package org.amoustakos.utils.io.realm

import io.reactivex.Flowable
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.kotlin.deleteFromRealm

/**
 * Utility class to help with Realm IO.
 *
 * Most of these methods can be implemented with [RealmObject] instead of [RealmModel]
 * but this would require you to extend this at model level instead of implement.
 */
@Suppress("UNCHECKED_CAST")
object RealmUtil : RealmTraits() {

    // =========================================================================================
    // Helpers
    // =========================================================================================

    @JvmStatic fun has(realm: Realm, model: Class<out RealmModel>): Boolean =
            realm.where(model).count() > 0

    @JvmStatic fun getCount(realm: Realm, model: Class<out RealmModel>): Long =
            realm.where(model).count()

    @JvmStatic fun <T : RealmModel> copyToRealm(realm: Realm, model: T) =
            add(realm, model, true)

    @JvmStatic fun <T : RealmModel> copyToRealmAsync(realm: Realm, model: T) =
            addAsync(realm, model, true)


    // =========================================================================================
    // CRUD - Synced
    // =========================================================================================

    @JvmStatic fun <T : RealmModel> copyFromRealm(
            realm: Realm, model: T, depth: Int): T? = (realm transWithResp {
        realm.copyFromRealm(model, depth)
    }) as T?

    @JvmStatic fun <T : RealmModel> add(realm: Realm, model: T, copy: Boolean): T {
        return (realm transWithResp {
            var obj: RealmModel? = model
            if (copy)
                obj = realm.copyToRealm(model)
            else
                realm.insert(model)
            obj
        }) as T
    }

    @JvmStatic fun addOrUpdate(realm: Realm, model: RealmModel) = realm trans {
        realm.insertOrUpdate(model)
    }

    @JvmStatic fun remove(realm: Realm, model: RealmModel) = realm trans {
        deleteNoTrans(model)
    }

    @JvmStatic fun remove(realm: Realm, models: List<RealmModel>) = realm trans {
        models.forEach { deleteNoTrans(it) }
    }

    @JvmStatic fun deleteNoTrans(model: RealmModel) = model.deleteFromRealm()

    @JvmStatic fun clearAll(realm: Realm, model: Class<out RealmModel>) = realm trans {
        realm.delete(model)
    }


    @JvmStatic fun <T : RealmModel> getByModel(realm: Realm, model: Class<T>): RealmResults<T> =
            realm.where(model).findAll()


    @JvmStatic fun <T : RealmModel> getOneByColumn(
            realm: Realm, model: Class<T>, column: String, value: String): T? =
            realm.where(model).equalTo(column, value).findFirst()

    @JvmStatic fun <T : RealmModel> getOneByColumn(
            realm: Realm, model: Class<T>, column: String, value: Int): T? =
            realm.where(model).equalTo(column, value).findFirst()

    @JvmStatic fun <T : RealmModel> getOneByColumn(
            realm: Realm, model: Class<T>, column: String, value: Double): T? =
            realm.where(model).equalTo(column, value).findFirst()

    @JvmStatic fun <T : RealmModel> getOneByColumn(
            realm: Realm, model: Class<T>, column: String, value: Float): T? =
            realm.where(model).equalTo(column, value).findFirst()

    @JvmStatic fun <T : RealmModel> getOneByColumn(
            realm: Realm, model: Class<T>, column: String, value: Boolean): T? =
            realm.where(model).equalTo(column, value).findFirst()


    @JvmStatic fun <T : RealmModel> getByColumn(
            realm: Realm, model: Class<T>, column: String, value: String): RealmResults<T> =
            realm.where(model).equalTo(column, value).findAll()

    @JvmStatic fun <T : RealmModel> getByColumn(
            realm: Realm, model: Class<T>, column: String, value: Int): RealmResults<T> =
            realm.where(model).equalTo(column, value).findAll()

    @JvmStatic fun <T : RealmModel> getByColumn(
            realm: Realm, model: Class<T>, column: String, value: Double): RealmResults<T> =
            realm.where(model).equalTo(column, value).findAll()

    @JvmStatic fun <T : RealmModel> getByColumn(
            realm: Realm, model: Class<T>, column: String, value: Float): RealmResults<T> =
            realm.where(model).equalTo(column, value).findAll()

    @JvmStatic fun <T : RealmModel> getByColumn(
            realm: Realm, model: Class<T>, column: String, value: Boolean): RealmResults<T> =
            realm.where(model).equalTo(column, value).findAll()


    // =========================================================================================
    // CRUD - Async
    // =========================================================================================

    @JvmStatic fun <T : RealmModel> copyFromRealmAsync(
            realm: Realm, model: T, depth: Int): Observable<T> =
            Observable.fromCallable {
                return@fromCallable (realm transWithResp {
                    realm.copyFromRealm(model, depth)
                }) as T
            }


    @JvmStatic fun <T : RealmModel> addAsync(realm: Realm, model: T, copy: Boolean): Observable<T> =
            Observable.fromCallable {
                return@fromCallable (realm transWithResp {
                    var obj: RealmModel = model
                    if (copy)
                        obj = realm.copyToRealm(model)
                    else
                        realm.insert(model)
                    obj
                }) as T
            }

    @JvmStatic fun addOrUpdateAsync(realm: Realm, model: RealmModel): Observable<Boolean> =
            Observable.fromCallable {
                realm trans { realm.insertOrUpdate(model) }
                return@fromCallable true
            }

    @JvmStatic fun removeAsync(realm: Realm, model: RealmModel): Observable<Boolean> =
            Observable.fromCallable {
                realm trans { deleteNoTrans(model) }
                return@fromCallable true
            }


    @JvmStatic fun removeAsync(realm: Realm, models: List<RealmModel>): Observable<Boolean> =
            Observable.fromCallable {
                realm trans {
                    models.forEach { deleteNoTrans(it) }
                }
                return@fromCallable true
            }


    @JvmStatic fun clearAllAsync(realm: Realm, model: Class<out RealmModel>): Observable<Boolean> =
            Observable.fromCallable {
                realm trans { realm.delete(model) }
                return@fromCallable true
            }


    @JvmStatic fun <T : RealmModel> getByModelAsync(
            realm: Realm, model: Class<T>): Flowable<RealmResults<T>> =
            realm.where(model).findAllAsync().asFlowable()


    @JvmStatic fun <T : RealmModel> getOneByColumnAsync(
            realm: Realm, model: Class<T>, column: String, value: String): T? =
            realm.where(model).equalTo(column, value).findFirstAsync()

    @JvmStatic fun <T : RealmModel> getOneByColumnAsync(
            realm: Realm, model: Class<T>, column: String, value: Int): T? =
            realm.where(model).equalTo(column, value).findFirstAsync()

    @JvmStatic fun <T : RealmModel> getOneByColumnAsync(
            realm: Realm, model: Class<T>, column: String, value: Double): T? =
            realm.where(model).equalTo(column, value).findFirstAsync()

    @JvmStatic fun <T : RealmModel> getOneByColumnAsync(
            realm: Realm, model: Class<T>, column: String, value: Float): T? =
            realm.where(model).equalTo(column, value).findFirstAsync()

    @JvmStatic fun <T : RealmModel> getOneByColumnAsync(
            realm: Realm, model: Class<T>, column: String, value: Boolean): T? =
            realm.where(model).equalTo(column, value).findFirstAsync()


    @JvmStatic fun <T : RealmModel> getByColumnAsync(
            realm: Realm, model: Class<T>, column: String, value: String): Flowable<RealmResults<T>> =
            realm.where(model).equalTo(column, value).findAllAsync().asFlowable()

    @JvmStatic fun <T : RealmModel> getByColumnAsync(
            realm: Realm, model: Class<T>, column: String, value: Int): Flowable<RealmResults<T>> =
            realm.where(model).equalTo(column, value).findAllAsync().asFlowable()

    @JvmStatic fun <T : RealmModel> getByColumnAsync(
            realm: Realm, model: Class<T>, column: String, value: Double): Flowable<RealmResults<T>> =
            realm.where(model).equalTo(column, value).findAllAsync().asFlowable()

    @JvmStatic fun <T : RealmModel> getByColumnAsync(
            realm: Realm, model: Class<T>, column: String, value: Float): Flowable<RealmResults<T>> =
            realm.where(model).equalTo(column, value).findAllAsync().asFlowable()

    @JvmStatic fun <T : RealmModel> getByColumnAsync(
            realm: Realm, model: Class<T>, column: String, value: Boolean): Flowable<RealmResults<T>> =
            realm.where(model).equalTo(column, value).findAllAsync().asFlowable()

}