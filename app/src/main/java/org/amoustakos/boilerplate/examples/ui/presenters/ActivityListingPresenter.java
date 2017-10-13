package org.amoustakos.boilerplate.examples.ui.presenters;

import org.amoustakos.boilerplate.examples.io.local.models.ActivityListingModel;
import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingContract;
import org.amoustakos.boilerplate.ui.activities.BaseActivity;
import org.amoustakos.boilerplate.ui.presenters.BasePresenter;
import org.amoustakos.boilerplate.util.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class ActivityListingPresenter<T extends ActivityListingContract.View> extends BasePresenter<T>
											implements ActivityListingContract.Actions {

	private String basePackage;


	public ActivityListingPresenter(String basePackage, T view) {
		super(view);
		this.basePackage = basePackage;
	}


	// =========================================================================================
	// Actions
	// =========================================================================================

	@Override
	public void load() {
		Observable.fromCallable(() -> {
				List<ActivityListingModel> models = new ArrayList<>();
				List<Class<BaseActivity>> activities = getActivities();

				for (Class<BaseActivity> act : activities) {
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
	// Reflection
	// =========================================================================================

	private List<Class<BaseActivity>> getActivities() {
		return ReflectionUtil.listClasses(basePackage);
	}


}
