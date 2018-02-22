package org.amoustakos.boilerplate.injection.module;

import android.app.Application;
import android.content.Context;

import org.amoustakos.boilerplate.Environment;
import org.amoustakos.boilerplate.injection.annotations.context.ApplicationContext;
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealmConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }


	// =========================================================================================
	// Base
	// =========================================================================================

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
    Environment provideEnvironment(@DefaultRealmConfig RealmConfiguration realmConfig) {
        return new Environment(mApplication, realmConfig);
    }



}
