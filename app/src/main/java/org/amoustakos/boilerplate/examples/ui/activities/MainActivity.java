package org.amoustakos.boilerplate.examples.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.amoustakos.boilerplate.R;
import org.amoustakos.boilerplate.examples.io.local.models.ActivityListingModel;
import org.amoustakos.boilerplate.examples.ui.adapters.ActivityListingAdapter;
import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingContract;
import org.amoustakos.boilerplate.examples.ui.presenters.ActivityListingPresenter;
import org.amoustakos.boilerplate.ui.activities.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity that lists all the activities in the same package and creates <br />
 * a {@link RecyclerView} that starts each one on click.
 */
public class MainActivity extends BaseActivity implements ActivityListingContract.View{

	private static String basePackage = MainActivity.class.getPackage().getName();

	// =========================================================================================
	// Variables
	// =========================================================================================

	private boolean doubleBackToExitPressedOnce = false;
	private ActivityListingContract.Actions mPresenter;

	private ActivityListingAdapter exampleAdapter;

	private Unbinder unbinder;

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
		unbinder = ButterKnife.bind(this);

		if (getToolbar() != null)
			getToolbar().setTitle(R.string.title_activity_main);

		if (mPresenter == null)
			mPresenter = new ActivityListingPresenter<>(this, getApplicationContext());

		setupRecycler();
		mPresenter.load();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbinder.unbind();
	}

	// =========================================================================================
	// View interactions
	// =========================================================================================

	@Override
	public void onItemsCollected(List<ActivityListingModel> items) {
		refreshAdapter(items);
	}


	// =========================================================================================
	// Setup
	// =========================================================================================

	private void refreshAdapter(List<ActivityListingModel> items) {
		exampleAdapter.clean();
		exampleAdapter.addAll(items);
		exampleAdapter.notifyDataSetChanged();
	}

	private void setupRecycler() {
		if (exampleAdapter == null)
			exampleAdapter = new ActivityListingAdapter(new ArrayList<>());
		final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		exampleRecycler.setLayoutManager(layoutManager);
		exampleRecycler.setAdapter(exampleAdapter);
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
