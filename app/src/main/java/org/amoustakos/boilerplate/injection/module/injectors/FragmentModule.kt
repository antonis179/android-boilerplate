package org.amoustakos.boilerplate.injection.module.injectors

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import org.amoustakos.boilerplate.injection.annotations.context.ActivityContext


@Module
class FragmentModule(
		private val mFragment: androidx.fragment.app.Fragment
) {

	@Provides
	@ActivityContext
	internal fun providesContext(): Context? = mFragment.activity

}
