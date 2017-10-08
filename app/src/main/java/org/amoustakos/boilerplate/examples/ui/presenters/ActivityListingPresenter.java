package org.amoustakos.boilerplate.examples.ui.presenters;

import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingContract;




public class ActivityListingPresenter<T extends ActivityListingContract.View> extends BasePresenter<T>
											implements ActivityListingContract.Actions {

	private String basePackage;


	public ActivityListingPresenter(String basePackage, T view) {
		super(view);
		this.basePackage = basePackage;
	}


}
