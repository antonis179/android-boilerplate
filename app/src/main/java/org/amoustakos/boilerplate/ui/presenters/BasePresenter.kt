package org.amoustakos.boilerplate.ui.presenters

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import org.amoustakos.boilerplate.ui.contracts.base.BaseContractView
import org.amoustakos.boilerplate.ui.contracts.base.BasePresenterActions
import java.lang.ref.WeakReference

open class BasePresenter<out T : BaseContractView>
protected constructor (
		view: T,
		val lifecycle: Lifecycle
) : DefaultLifecycleObserver, BasePresenterActions {

	protected val mView: WeakReference<out T> = WeakReference(view)


	protected fun view() = mView.get()!!



	override fun subscribeToLifecycle(lifecycle: Lifecycle) {
		lifecycle.addObserver(this)
	}

	override fun unsubscribeFromLifecycle(lifecycle: Lifecycle) {
		lifecycle.removeObserver(this)
	}

}
