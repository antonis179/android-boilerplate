package org.amoustakos.boilerplate.injection.module;

import android.app.Application;
import android.content.Context;

import org.amoustakos.boilerplate.Environment;
import org.amoustakos.boilerplate.injection.annotations.context.ApplicationContext;

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
    Environment provideEnvironment() {
        return new Environment(mApplication);
    }



}
