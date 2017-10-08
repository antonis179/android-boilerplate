package org.amoustakos.boilerplate.examples.io.local.dao

import io.realm.Realm
import io.realm.RealmResults
import org.amoustakos.boilerplate.examples.io.local.models.ExampleModel
import org.amoustakos.boilerplate.examples.io.local.models.ExampleModel.Column.ID
import org.amoustakos.boilerplate.examples.io.local.models.ExampleModel.Column.NAME
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealm
import org.amoustakos.boilerplate.io.db.dao.base.BaseDao
import javax.inject.Inject


class ExampleDao @Inject
constructor(@DefaultRealm realm: Realm) : BaseDao<ExampleModel>(realm, ExampleModel::class.java) {



    // =========================================================================================
    // CRUD
    // =========================================================================================

    fun getByID(id: String): ExampleModel? = getOneByColumn(ID, id)

    fun getByName(name: String): RealmResults<ExampleModel> =
            getByColumn(NAME, name)



}
