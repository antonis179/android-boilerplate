package org.amoustakos.boilerplate.di.component


import androidx.fragment.app.Fragment
import dagger.Subcomponent
import org.amoustakos.boilerplate.di.annotations.scopes.PerActivity
import org.amoustakos.boilerplate.di.module.injectors.FragmentModule

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerActivity
@Subcomponent(modules = [
	FragmentModule::class
])
interface FragmentComponent {

	fun inject(fragment: Fragment)

}
