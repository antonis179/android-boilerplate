package org.amoustakos.boilerplate.di.module.injectors

import dagger.Module
import dagger.Provides
import org.amoustakos.boilerplate.di.annotations.context.ActivityContext
import org.amoustakos.boilerplate.ui.activities.BaseActivity

@Module
class ActivityModule(
		private val mActivity: BaseActivity
) {

	@Provides
	@ActivityContext
	internal fun providesContext(): BaseActivity = mActivity


}
