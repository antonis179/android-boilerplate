package org.amoustakos.boilerplate;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import org.amoustakos.boilerplate.injection.component.ApplicationComponent;
import org.amoustakos.boilerplate.injection.component.DaggerApplicationComponent;
import org.amoustakos.boilerplate.injection.module.ApplicationModule;


public class BoilerplateApplication extends Application {
	ApplicationComponent mApplicationComponent;


    /*
     * TODO:
     *  - Add uncaught exception handler
     *  - Add low memory handler
     */

	@Override
	public void onCreate() {
		super.onCreate();
        /*
         * Create the application component used throughout the app.
         */
		mApplicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(new ApplicationModule(this))
				.build();

        /*
         * Init app environment.
         * logs, db, fonts depend on this.
         */
		getComponent().environment().init();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		System.gc();
	}

	public static BoilerplateApplication get(Context context) {
		return (BoilerplateApplication) context.getApplicationContext();
	}
	public ApplicationComponent getComponent() {
		return mApplicationComponent;
	}

}
