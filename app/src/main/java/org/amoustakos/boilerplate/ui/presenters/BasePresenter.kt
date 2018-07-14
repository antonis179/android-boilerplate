package org.amoustakos.boilerplate.ui.presenters

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.Lifecycle
import io.reactivex.disposables.Disposable
import org.amoustakos.cryptoranker.ui.contracts.base.BaseContractView
import org.amoustakos.utils.android.LifecycleDisposableList
import org.amoustakos.utils.android.LifecycleDisposableOpts

/**
 * Created by Antonis Moustakos on 10/8/2017.
 */

open class BasePresenter<out T : BaseContractView>
protected constructor(protected val mView: T) : DefaultLifecycleObserver {

	@set:Synchronized
	@get:Synchronized
	var disposables: LifecycleDisposableList = LifecycleDisposableList(
			LifecycleDisposableOpts(false, false, true))

	init {
		disposables.initSubscriptions()
	}


	fun subscribeToLifecycle(lifecycle: Lifecycle) {
		lifecycle.addObserver(this)
		disposables.subscribeToLifecycle(lifecycle)
	}

	fun unsubscribeToLifecycle(lifecycle: Lifecycle) {
		lifecycle.removeObserver(this)
		disposables.unsubscribeToLifecycle(lifecycle)
	}


	// =========================================================================================
	// Subscriptions
	// =========================================================================================

	private fun initSubscriptions() {
		disposables.initSubscriptions()
	}

	fun addLifecycleDisposable(disposable: Disposable) = disposables.add(disposable)

	fun removeLifecycleDisposable(disposable: Disposable) = disposables.remove(disposable)

	fun clearLifecycleDisposables() {
		disposables.clear()
	}
}
