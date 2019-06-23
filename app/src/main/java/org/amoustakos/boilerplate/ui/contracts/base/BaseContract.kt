package org.amoustakos.boilerplate.ui.contracts.base

import androidx.lifecycle.Lifecycle

interface BasePresenterActions {
	fun init() {}

	fun subscribeToLifecycle(lifecycle: Lifecycle)
	fun unsubscribeFromLifecycle(lifecycle: Lifecycle)
}

interface BaseContractView