package org.amoustakos.boilerplate.examples.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.amoustakos.boilerplate.R;
import org.amoustakos.boilerplate.examples.io.local.models.ActivityListingModel;
import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingContract;
import org.amoustakos.boilerplate.examples.ui.presenters.ActivityListingPresenter;
import org.amoustakos.boilerplate.ui.activities.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that lists all the activities in the same package and creates <br />
 * a {@link RecyclerView} that starts each one on click.
 */
public class MainActivity extends BaseActivity implements ActivityListingContract.View{

	private static final String basePackage = MainActivity.class.getPackage().getName();

	// =========================================================================================
	// Variables
	// =========================================================================================

	private boolean doubleBackToExitPressedOnce = false;
	private ActivityListingPresenter<MainActivity> mPresenter;

	// =========================================================================================
	// Views
	// =========================================================================================

	@BindView(R.id.rv_activity_pool)
	RecyclerView exampleRecycler;


	// =========================================================================================
	// Overrides
	// =========================================================================================

	@Override
	@LayoutRes protected int layoutId() {
		return R.layout.activity_main;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.bind(this);

		if (getToolbar() != null)
			getToolbar().setTitle(R.string.title_activity_main);

		if (mPresenter == null)
			mPresenter = new ActivityListingPresenter<>(basePackage, this);
	}


	@Override
	public void onBackPressed() {
		if (isDoubleBackToExitPressedOnce()) {
			super.onBackPressed();
			return;
		}
		setDoubleBackToExitPressedOnce(true);
		Toast.makeText(this, getString(R.string.toast_back_to_exit), Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(()->setDoubleBackToExitPressedOnce(false), 2000);
	}


	// =========================================================================================
	// View interactions
	// =========================================================================================

	@Override
	public void onItemsCollected(List<ActivityListingModel> items) {

	}


	// =========================================================================================
	// Getters - Setters
	// =========================================================================================

	private synchronized boolean isDoubleBackToExitPressedOnce() {
		return doubleBackToExitPressedOnce;
	}
	private synchronized void setDoubleBackToExitPressedOnce(boolean doubleBackToExitPressedOnce) {
		this.doubleBackToExitPressedOnce = doubleBackToExitPressedOnce;
	}


}
