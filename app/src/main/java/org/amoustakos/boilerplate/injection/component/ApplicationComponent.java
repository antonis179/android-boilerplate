package org.amoustakos.boilerplate.injection.component;

import android.app.Application;
import android.content.Context;

import org.amoustakos.boilerplate.Environment;
import org.amoustakos.boilerplate.injection.ApplicationContext;
import org.amoustakos.boilerplate.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {



    /*
     * Base
     */
    @ApplicationContext
    Context context();
    Application application();
    Environment environment();



//    ApiService apiService();
//    PreferencesHelper preferencesHelper();
//    RealmManager databaseHelper();
//    DataManager dataManager();


}
