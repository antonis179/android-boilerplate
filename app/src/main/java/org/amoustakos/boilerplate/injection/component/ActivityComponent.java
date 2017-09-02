package org.amoustakos.boilerplate.injection.component;


import android.app.Activity;

import org.amoustakos.boilerplate.injection.ActivityContext;
import org.amoustakos.boilerplate.injection.PerActivity;
import org.amoustakos.boilerplate.injection.module.ActivityModule;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {


    void inject(Activity activity);



    @ActivityContext
    Activity activity();

}
