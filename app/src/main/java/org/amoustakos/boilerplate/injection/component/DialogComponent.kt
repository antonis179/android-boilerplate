package org.amoustakos.boilerplate.injection.component


import android.app.Dialog
import dagger.Subcomponent
import org.amoustakos.boilerplate.injection.annotations.scopes.PerActivity
import org.amoustakos.boilerplate.injection.module.injectors.DialogModule

/**
 * This component injects dependencies to all Dialogs across the application
 */
@PerActivity
@Subcomponent(modules = [
	DialogModule::class
])
interface DialogComponent {

	fun inject(dlg: Dialog)

}
