package org.amoustakos.boilerplate.injection.component;

import android.app.Application;
import android.content.Context;

import org.amoustakos.boilerplate.Environment;
import org.amoustakos.boilerplate.examples.io.local.dao.ExampleDao;
import org.amoustakos.boilerplate.injection.annotations.context.ApplicationContext;
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealm;
import org.amoustakos.boilerplate.injection.module.ApplicationModule;
import org.amoustakos.boilerplate.injection.module.DBModule;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

@Singleton
@Component(modules = {
		ApplicationModule.class,
		DBModule.class
})
public interface ApplicationComponent {


    /*
     * Base
     */
    @ApplicationContext
    Context context();
    Application application();
    Environment environment();


	/*
	 * DB
	 */
	@DefaultRealm Realm realm();



	/*
	 * DAOs
	 */
	ExampleDao exampleDao();



//    ApiService apiService();
//    PreferencesHelper preferencesHelper();
//    RealmManager databaseHelper();
//    DataManager dataManager();


}
