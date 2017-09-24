package org.amoustakos.boilerplate.examples.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

import org.amoustakos.boilerplate.R;
import org.amoustakos.boilerplate.ui.activities.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {



	@BindView(R.id.rv_activity_pool)
	RecyclerView exampleRecycler;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	@LayoutRes protected int layoutId() {
		return R.layout.activity_main;
	}


}
