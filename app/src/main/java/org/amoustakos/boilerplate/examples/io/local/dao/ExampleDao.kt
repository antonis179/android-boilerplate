package org.amoustakos.boilerplate.examples.io.local.dao

import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import org.amoustakos.boilerplate.examples.io.local.models.ExampleModel
import org.amoustakos.boilerplate.examples.io.local.models.ExampleModel.Column.ID
import org.amoustakos.boilerplate.examples.io.local.models.ExampleModel.Column.NAME
import org.amoustakos.boilerplate.di.annotations.realm.DefaultRealmConfig
import org.amoustakos.boilerplate.io.realm.dao.base.BaseDao
import org.amoustakos.utils.android.rx.RxUtil


class ExampleDao(realm: Realm):
		BaseDao<ExampleModel>(realm, ExampleModel::class.java) {


    fun getByID(id: String): ExampleModel? = getOneByColumn(ID, id)

    fun getByName(name: String): RealmResults<ExampleModel> =
            getByColumn(NAME, name)



    companion object {

        fun getInstance(@DefaultRealmConfig config: RealmConfiguration) =
                ExampleDao(Realm.getInstance(config))

        fun getObservableInstance(
                @DefaultRealmConfig config: RealmConfiguration): Observable<ExampleDao> =
		        Observable.just {}
                        .compose(RxUtil.applyRealmObservableScheduler())
				        .map { ExampleDao(Realm.getInstance(config)) }


    }
}
