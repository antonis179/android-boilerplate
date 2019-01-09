package org.amoustakos.boilerplate.injection.component


import androidx.fragment.app.Fragment
import dagger.Subcomponent
import org.amoustakos.boilerplate.injection.annotations.scopes.PerActivity
import org.amoustakos.boilerplate.injection.module.injectors.FragmentModule

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerActivity
@Subcomponent(modules = [
	FragmentModule::class
])
interface FragmentComponent {

	fun inject(fragment: androidx.fragment.app.Fragment)

}
