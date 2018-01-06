package org.amoustakos.boilerplate.injection.component;


import android.app.Fragment;

import org.amoustakos.boilerplate.injection.annotations.scopes.PerActivity;
import org.amoustakos.boilerplate.injection.module.FragmentModule;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerActivity
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {


    // =========================================================================================
    // Injections
    // =========================================================================================

    void inject(Fragment fragment);

}
