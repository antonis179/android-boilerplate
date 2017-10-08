package org.amoustakos.boilerplate.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.LongSparseArray;
import android.view.View;
import android.view.ViewGroup;

import org.amoustakos.boilerplate.BoilerplateApplication;
import org.amoustakos.boilerplate.R;
import org.amoustakos.boilerplate.injection.component.ActivityComponent;
import org.amoustakos.boilerplate.injection.component.ConfigPersistentComponent;
import org.amoustakos.boilerplate.injection.component.DaggerConfigPersistentComponent;
import org.amoustakos.boilerplate.injection.module.ActivityModule;

import java.util.concurrent.atomic.AtomicLong;

import butterknife.ButterKnife;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
	private static final LongSparseArray<ConfigPersistentComponent> sComponentsMap = new LongSparseArray<>();

    private ActivityComponent mActivityComponent;
    private long mActivityId;

    private ViewGroup rootView;


	/*
	 * Overridden
	 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    setContentView(layoutId());
	    makeID(savedInstanceState);
	    makeComponents(mActivityId);

	    Toolbar toolbar = getToolbar();
	    if (toolbar != null)
	    	setSupportActionBar(toolbar);
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong(KEY_ACTIVITY_ID, mActivityId);
	}

	@Override
	protected void onDestroy() {
		if (!isChangingConfigurations()) {
			Timber.d("Clearing ConfigPersistentComponent id=%d", mActivityId);
			sComponentsMap.remove(mActivityId);
		}
		super.onDestroy();
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		//Calligraphy
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}


    /*
     * Injection
     */

    private void makeID(Bundle savedInstanceState) {
	    mActivityId = savedInstanceState != null ?
			    savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
    }

    private void makeComponents(long activityID) {
	    ConfigPersistentComponent configPersistentComponent;
	    int index = sComponentsMap.indexOfKey(activityID);
	    if (index < 0) { //component does not exist
		    Timber.d("Creating new ConfigPersistentComponent id=%d", activityID);
		    configPersistentComponent = DaggerConfigPersistentComponent.builder()
				    .applicationComponent(application().getComponent())
				    .build();
		    sComponentsMap.put(activityID, configPersistentComponent);
	    }
	    else {
		    Timber.d("Reusing ConfigPersistentComponent id=%d", activityID);
		    configPersistentComponent = sComponentsMap.get(index);
	    }
	    mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
    }




    /*
     * View helpers
     */
    @LayoutRes protected abstract int layoutId();

    protected View getRootView() {
	    return ((ViewGroup)ButterKnife.findById(this, android.R.id.content)).getChildAt(0);
    }

    @Nullable protected Toolbar getToolbar() {
	    return (Toolbar) findViewById(R.id.toolbar);
    }




    /*
     * Getters
     */

    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }

    public BoilerplateApplication application() {
	    return BoilerplateApplication.get(this);
    }

}
