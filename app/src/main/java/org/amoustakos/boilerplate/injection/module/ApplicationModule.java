package org.amoustakos.boilerplate.injection.module;

import android.app.Application;
import android.content.Context;

import org.amoustakos.boilerplate.Environment;
import org.amoustakos.boilerplate.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }



    /*
     * Base
     */
    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Environment provideEnvironment() {
        return new Environment(mApplication);
    }








//    @Provides
//    @Singleton
//    ApiService provideApiService() {
//        return ApiService.Creator.newApiService();
//    }

//    @Provides
//    @Singleton
//    RealmManager provideDatabaseHelper() {
//        return new RealmManager();
//    }

//    @Provides
//    @Singleton
//    PreferencesHelper providePreferencesHelper() {
//        return new PreferencesHelper(mApplication);
//    }

//    @Provides
//    @Singleton
//    DataManager provideDataManager(
//                                        ApiService apiService,
//                                        PreferencesHelper preferencesHelper,
//                                        RealmManager databaseHelper
//                                    ) {
//        return new DataManager(apiService, preferencesHelper, databaseHelper);
//    }

}
