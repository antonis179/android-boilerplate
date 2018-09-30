package org.amoustakos.boilerplate.injection.component


import android.app.Activity
import dagger.Subcomponent
import org.amoustakos.boilerplate.examples.ui.activities.ListingActivity
import org.amoustakos.boilerplate.injection.annotations.context.ActivityContext
import org.amoustakos.boilerplate.injection.annotations.scopes.PerActivity
import org.amoustakos.boilerplate.injection.module.injectors.ActivityModule
import org.amoustakos.boilerplate.ui.activities.BaseActivity

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = [
	ActivityModule::class
])
interface ActivityComponent {


	// =========================================================================================
	// Injections
	// =========================================================================================

	fun inject(activity: Activity)
	fun inject(activity: ListingActivity)


	// =========================================================================================
	// Base
	// =========================================================================================

	@ActivityContext
	fun activity(): BaseActivity

}
