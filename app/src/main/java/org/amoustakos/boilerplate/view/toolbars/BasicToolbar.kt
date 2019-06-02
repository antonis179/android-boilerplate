package org.amoustakos.boilerplate.view.toolbars

import android.app.Activity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.amoustakos.boilerplate.R
import org.amoustakos.boilerplate.view.base.ActivityViewComponent
import org.amoustakos.boilerplate.view.toolbars.base.ToolbarView
import java.lang.ref.WeakReference

open class BasicToolbar(private val id: Int) : ActivityViewComponent, ToolbarView {

	operator fun invoke() = get()

	protected var activity: WeakReference<AppCompatActivity?> = WeakReference(null)
	protected var toolbar: Toolbar? = null
	protected var title: TextView? = null


	override fun setup(activity: Activity) {
		toolbar = activity.findViewById(id)
		title = toolbar?.findViewById(R.id.tvToolbarTitle)

		setAsActionbar(activity as AppCompatActivity)
		this.activity = WeakReference(activity)
	}


	override fun get() = toolbar

	override fun setAsActionbar(activity: AppCompatActivity) {
		toolbar?.let { activity.setSupportActionBar(it) }
	}

	override fun toggleBackButton(enabled: Boolean) {
		activity.get()?.supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
		activity.get()?.supportActionBar?.setDisplayShowHomeEnabled(enabled)
	}

	override fun setTitle(title: String) {
		this.title?.text = title
	}

	override fun setTitle(title: CharSequence) {
		this.title?.text = title
	}

	override fun setTitle(titleResource: Int) {
		this.title?.setText(titleResource)
	}
}