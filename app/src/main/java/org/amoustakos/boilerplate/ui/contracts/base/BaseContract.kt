package org.amoustakos.boilerplate.ui.contracts.base

import android.arch.lifecycle.Lifecycle

interface BaseContractActions {
	fun init() {}

	fun subscribeToLifecycle(lifecycle: Lifecycle)
	fun unsubscribeFromLifecycle(lifecycle: Lifecycle)
}

interface BaseContractView