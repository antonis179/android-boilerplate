package org.amoustakos.utils.android.rx.disposer

import android.arch.lifecycle.GenericLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver


internal class LifecycleDisposer(
    lifecycle: Lifecycle,
    vararg events: Lifecycle.Event
) : Disposer by SyncedDisposer() {

    private val observer: LifecycleObserver = GenericLifecycleObserver { _, event ->
        if (events.contains(event))     dispose()
    }

    init {
        lifecycle.addObserver(observer)
    }
}