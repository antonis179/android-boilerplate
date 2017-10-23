package org.amoustakos.boilerplate.examples.ui.presenters;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import org.amoustakos.boilerplate.examples.io.local.models.ActivityListingModel;
import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingContract;
import org.amoustakos.boilerplate.ui.activities.BaseActivity;
import org.amoustakos.boilerplate.ui.presenters.BasePresenter;

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


	public ActivityListingPresenter(String basePackage, T view, @NonNull Context context) {
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
			List<Class<? extends BaseActivity>> activities = getActivities();

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
	//
	// =========================================================================================

	private List<Class<? extends BaseActivity>> getActivities() {
		List<Class<? extends BaseActivity>> activities = new ArrayList<>();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(basePackage, PackageManager.GET_ACTIVITIES);
			ActivityInfo[] act = packageInfo.activities;
			for (ActivityInfo ai : act) {
				try {
					if (!ai.packageName.equals(basePackage)) continue;
					Class<? extends BaseActivity> cl = Class.forName(ai.name).asSubclass(BaseActivity.class);
					activities.add(cl);
				} catch (ClassNotFoundException | ClassCastException cle) {
					Timber.v(cle);
				}
			}
		} catch (PackageManager.NameNotFoundException e) {
			Timber.e(e);
		}

		return activities;
	}


}
