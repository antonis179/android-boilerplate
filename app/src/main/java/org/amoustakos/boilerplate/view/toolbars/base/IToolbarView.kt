package org.amoustakos.boilerplate.view.toolbars.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


interface IToolbarView {

	fun get(): Toolbar?

	fun setAsActionbar(activity: AppCompatActivity)

	fun setTitle(title: String)
	fun setTitle(title: CharSequence)
	fun setTitle(titleResource: Int)

}