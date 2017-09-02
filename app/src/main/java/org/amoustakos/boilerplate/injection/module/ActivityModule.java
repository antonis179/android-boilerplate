package org.amoustakos.boilerplate.injection.module;

import android.app.Activity;

import org.amoustakos.boilerplate.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Activity providesContext() {
        return mActivity;
    }


    /*
     * Adapters
     */
}
