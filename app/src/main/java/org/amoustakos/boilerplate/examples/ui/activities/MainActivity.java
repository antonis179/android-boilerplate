package org.amoustakos.boilerplate.examples.ui.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

import org.amoustakos.boilerplate.R;
import org.amoustakos.boilerplate.ui.activities.base.BaseActivity;
import org.amoustakos.boilerplate.util.ReflectionUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that lists all the activities in the same package and creates <br />
 * a {@link RecyclerView} that starts each one on click.
 */
public class MainActivity extends BaseActivity {

	private static final String basePackage = MainActivity.class.getPackage().getName();


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
	}


	// =========================================================================================
	// Reflection
	// =========================================================================================

	private List<Class<BaseActivity>> getActivities() {
		return ReflectionUtil.listClasses(basePackage);
	}




}
