package org.amoustakos.boilerplate.view.toolbars

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.amoustakos.boilerplate.view.base.IActivityViewComponent
import org.amoustakos.boilerplate.view.toolbars.base.IToolbarView

class BasicToolbar(val id: Int): IActivityViewComponent, IToolbarView {

	private var toolbar: Toolbar? = null


	override fun setup(activity: Activity) {
		toolbar = activity.findViewById(id)
	}



	override fun get() = toolbar

	override fun setAsActionbar(activity: AppCompatActivity) {
		if (toolbar != null)
			activity.setSupportActionBar(toolbar)
	}

	override fun setTitle(title: String) {
		toolbar?.title = title
	}

	override fun setTitle(title: CharSequence) {
		toolbar?.title = title
	}

	override fun setTitle(titleResource: Int) {
		toolbar?.setTitle(titleResource)
	}
}