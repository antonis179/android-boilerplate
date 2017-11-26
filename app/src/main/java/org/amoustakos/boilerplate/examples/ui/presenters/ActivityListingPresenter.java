package org.amoustakos.boilerplate.examples.ui.presenters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import org.amoustakos.boilerplate.examples.io.local.models.ActivityListingModel;
import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingContract;
import org.amoustakos.boilerplate.ui.activities.BaseActivity;
import org.amoustakos.boilerplate.ui.presenters.BasePresenter;
import org.amoustakos.utils.android.PackageManagerUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class ActivityListingPresenter<T extends ActivityListingContract.View> extends BasePresenter<T>
											implements ActivityListingContract.Actions {

	private String basePackage;
	private PackageManager pm;


	public ActivityListingPresenter(T view, @NonNull Context context) {
		super(view);
		this.basePackage = context.getPackageName();
		pm = context.getPackageManager();
	}


	// =========================================================================================
	// Actions
	// =========================================================================================

	@Override
	public void load() {
		Observable.fromCallable(() -> {
				List<ActivityListingModel> models = new ArrayList<>();
			List<Class<? extends BaseActivity>> activities = excludeCurrent(getActivities());

			for (Class<? extends BaseActivity> act : activities) {
					ActivityListingModel model = new ActivityListingModel(
							act,
							act.getName(),
							act.getSuperclass().getName()
					);
					models.add(model);
				}

				Timber.i("Found " + activities.size() + " classes");

				return models;
			})
			.observeOn(Schedulers.computation())
			.onErrorReturn(t -> new ArrayList<>())
			.doOnError(Timber::i)
			.observeOn(AndroidSchedulers.mainThread())
			.doOnNext(mView::onItemsCollected)
			.subscribe();
	}



	// =========================================================================================
	// Helpers
	// =========================================================================================

	private List<Class<? extends BaseActivity>> getActivities() {
		return PackageManagerUtils.getDefinedActivities(pm, basePackage);
	}

	private List<Class<? extends BaseActivity>> excludeCurrent(List<Class<? extends BaseActivity>> classes) {
		Class<?> current = mView.getClass();
		Integer toRemove = null;

		int count = 0;
		for (Class<? extends BaseActivity> cl : classes) {
			if (current == cl) {
				toRemove = count;
				break;
			}
			count++;
		}

		if (toRemove != null)
			classes.remove(toRemove.intValue());

		return classes;
	}

}
