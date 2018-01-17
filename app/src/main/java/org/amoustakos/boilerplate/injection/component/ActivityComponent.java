package org.amoustakos.boilerplate.injection.component;


import android.app.Activity;

import org.amoustakos.boilerplate.examples.ui.activities.MainActivity;
import org.amoustakos.boilerplate.injection.annotations.context.ActivityContext;
import org.amoustakos.boilerplate.injection.annotations.scopes.PerActivity;
import org.amoustakos.boilerplate.injection.module.ActivityModule;
import org.amoustakos.boilerplate.ui.activities.BaseActivity;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {


    // =========================================================================================
    // Injections
    // =========================================================================================

    void inject(Activity activity);
    void inject(MainActivity activity);


    // =========================================================================================
    // Base
    // =========================================================================================

    @ActivityContext BaseActivity activity();

}
