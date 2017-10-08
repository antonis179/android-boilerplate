package org.amoustakos.boilerplate.injection.component;


import android.app.Dialog;

import org.amoustakos.boilerplate.injection.PerActivity;
import org.amoustakos.boilerplate.injection.module.DialogModule;

import dagger.Subcomponent;

/**
 * This component injects dependencies to all Dialogs across the application
 */
@PerActivity
@Subcomponent(modules = DialogModule.class)
public interface DialogComponent {

    // =========================================================================================
    // Injections
    // =========================================================================================

    void inject(Dialog dlg);

}
