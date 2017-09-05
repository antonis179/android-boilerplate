package org.amoustakos.boilerplate.injection.module;

import android.app.Dialog;
import android.content.Context;

import org.amoustakos.boilerplate.injection.annotations.context.ActivityContext;

import dagger.Module;
import dagger.Provides;


@Module
public class DialogModule {
    private Dialog mDialog;

    public DialogModule(Dialog dialog) {
        mDialog = dialog;
    }


    @Provides
    @ActivityContext
    Context providesContext() {
        return mDialog.getContext();
    }
}
