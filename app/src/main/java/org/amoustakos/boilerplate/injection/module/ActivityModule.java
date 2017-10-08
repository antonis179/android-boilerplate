package org.amoustakos.boilerplate.injection.module;

import org.amoustakos.boilerplate.injection.annotations.context.ActivityContext;
import org.amoustakos.boilerplate.ui.activities.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private BaseActivity mActivity;

    public ActivityModule(BaseActivity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    BaseActivity providesContext() {
        return mActivity;
    }


}
