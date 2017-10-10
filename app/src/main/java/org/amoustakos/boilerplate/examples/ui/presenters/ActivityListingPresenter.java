package org.amoustakos.boilerplate.examples.ui.presenters;

import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingContract;
import org.amoustakos.boilerplate.ui.activities.BaseActivity;
import org.amoustakos.boilerplate.ui.presenters.BasePresenter;
import org.amoustakos.boilerplate.util.ReflectionUtil;

import java.util.List;


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

	}



	// =========================================================================================
	// Reflection
	// =========================================================================================

	private List<Class<BaseActivity>> getActivities() {
		return ReflectionUtil.listClasses(basePackage);
	}


}
