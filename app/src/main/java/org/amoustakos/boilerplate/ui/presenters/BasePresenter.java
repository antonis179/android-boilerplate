package org.amoustakos.boilerplate.ui.presenters;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import org.amoustakos.boilerplate.ui.contracts.BaseContractView;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;

/**
 * Created by Antonis Moustakos on 10/8/2017.
 */

public class BasePresenter<T extends BaseContractView> implements DefaultLifecycleObserver{

	protected T mView;

	protected ListCompositeDisposable rxDisposables;


	// =========================================================================================
	// Constructors
	// =========================================================================================

	protected BasePresenter(T mView) {
		this.mView = mView;
	}


	// =========================================================================================
	// Subscriptions
	// =========================================================================================

	private void initSubscriptions() {
		rxDisposables = new ListCompositeDisposable();
	}

	public boolean addLifecycleBoundDisposable(Disposable disposable) {
		if (rxDisposables.isDisposed()) return false;
		return rxDisposables.add(disposable);
	}


	// =========================================================================================
	// Lifecycle
	// =========================================================================================

	@Override public void onCreate(@NonNull LifecycleOwner owner) {}
	@Override public void onStart(@NonNull LifecycleOwner owner) {}
	@Override public void onResume(@NonNull LifecycleOwner owner) {}
	@Override public void onPause(@NonNull LifecycleOwner owner) {}
	@Override public void onStop(@NonNull LifecycleOwner owner) {}

	@Override
	public void onDestroy(@NonNull LifecycleOwner owner) {
		rxDisposables.dispose();
	}

	// =========================================================================================
	// Getters - Setters
	// =========================================================================================

	public ListCompositeDisposable getRxDisposables() {
		return rxDisposables;
	}

	public synchronized void setRxDisposables(ListCompositeDisposable rxDisposables) {
		this.rxDisposables = rxDisposables;
	}
}
