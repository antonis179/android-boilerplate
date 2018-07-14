package org.amoustakos.boilerplate.injection.module.injectors

import dagger.Module
import dagger.Provides
import org.amoustakos.boilerplate.injection.annotations.context.ActivityContext
import org.amoustakos.boilerplate.ui.activities.BaseActivity

@Module
class ActivityModule(
		private val mActivity: BaseActivity
) {

	@Provides
	@ActivityContext
	internal fun providesContext(): BaseActivity = mActivity


}
