package org.amoustakos.boilerplate.examples.ui.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

import org.amoustakos.boilerplate.R;
import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingContract;
import org.amoustakos.boilerplate.examples.ui.presenters.ActivityListingPresenter;
import org.amoustakos.boilerplate.ui.activities.BaseActivity;
import org.amoustakos.boilerplate.util.ReflectionUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that lists all the activities in the same package and creates <br />
 * a {@link RecyclerView} that starts each one on click.
 */
public class MainActivity extends BaseActivity implements ActivityListingContract.View{

	private static final String basePackage = MainActivity.class.getPackage().getName();


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


	// =========================================================================================
	// Reflection
	// =========================================================================================

	private List<Class<BaseActivity>> getActivities() {
		return ReflectionUtil.listClasses(basePackage);
	}




}
