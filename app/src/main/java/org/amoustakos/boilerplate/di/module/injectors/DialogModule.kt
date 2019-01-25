package org.amoustakos.boilerplate.di.module.injectors

import android.app.Dialog
import android.content.Context
import dagger.Module
import dagger.Provides
import org.amoustakos.boilerplate.di.annotations.context.ActivityContext


@Module
class DialogModule(
		private val mDialog: Dialog
) {

	@Provides
	@ActivityContext
	internal fun providesContext(): Context = mDialog.context

}
