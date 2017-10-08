package org.amoustakos.boilerplate.examples.ui.presenters;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import org.amoustakos.boilerplate.examples.ui.contracts.BaseContractView;

/**
 * Created by Antonis Moustakos on 10/8/2017.
 */

public class BasePresenter<T extends BaseContractView> implements DefaultLifecycleObserver{

	protected T mView;


	protected BasePresenter(T mView) {
		this.mView = mView;
	}


	// =========================================================================================
	// Lifecycle
	// =========================================================================================
	@Override public void onCreate(@NonNull LifecycleOwner owner) {}
	@Override public void onStart(@NonNull LifecycleOwner owner) {}
	@Override public void onResume(@NonNull LifecycleOwner owner) {}
	@Override public void onPause(@NonNull LifecycleOwner owner) {}
	@Override public void onStop(@NonNull LifecycleOwner owner) {}
	@Override public void onDestroy(@NonNull LifecycleOwner owner) {}
}
