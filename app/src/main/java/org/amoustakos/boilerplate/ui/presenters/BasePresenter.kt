package org.amoustakos.boilerplate.ui.presenters

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import org.amoustakos.boilerplate.ui.contracts.base.BaseViewContract
import org.amoustakos.boilerplate.ui.contracts.base.BasePresenterContract
import java.lang.ref.WeakReference

open class BasePresenter<out T : BaseViewContract>
protected constructor (
		view: T,
		val lifecycle: Lifecycle
) : DefaultLifecycleObserver, BasePresenterContract {

	protected val mView: WeakReference<out T> = WeakReference(view)


	protected fun view() = mView.get()!!



	override fun subscribeToLifecycle(lifecycle: Lifecycle) {
		lifecycle.addObserver(this)
	}

	override fun unsubscribeFromLifecycle(lifecycle: Lifecycle) {
		lifecycle.removeObserver(this)
	}

}
