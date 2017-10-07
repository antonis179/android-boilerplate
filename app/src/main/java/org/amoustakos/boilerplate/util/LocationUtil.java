package org.amoustakos.boilerplate.util;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.amoustakos.boilerplate.injection.annotations.context.ApplicationContext;

import java.lang.annotation.Retention;
import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static java.lang.annotation.RetentionPolicy.SOURCE;

//TODO: Add lifecycle observable
public class LocationUtil implements GoogleApiClient.ConnectionCallbacks,
										GoogleApiClient.OnConnectionFailedListener,
										LocationListener,
										Application.ActivityLifecycleCallbacks {

	public static final String STATE_CONNECTED = "CONNECTED";
	public static final String STATE_CONNECTING = "CONNECTING";
	public static final String STATE_DISCONNECTED = "DISCONNECTED";
	public static final String STATE_DISCONNECTING = "DISCONNECTING";


	// Defaults
	public long mUpdateInterval = 120000;
	public long mFastestUpdateInterval = mUpdateInterval / 2;
	public int mRequestPriority = LocationRequest.PRIORITY_LOW_POWER;

	//Publishers
	private final PublishSubject<Location> locationSubject = PublishSubject.create();
	private final PublishSubject<String> stateSubject = PublishSubject.create();

	//Client
	private GoogleApiClient mGoogleApiClient;
	private LocationRequest mLocationRequest;

	//State
	public boolean mRequestingLocationUpdates;
	@State public volatile String state = STATE_DISCONNECTED;


	private Location mCurrentLocation;

	private final WeakReference<Context> context;


	///////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////

	public LocationUtil(@ApplicationContext Context context) {
		this.context = new WeakReference<>(context);
		init();
	}

	public LocationUtil(@ApplicationContext Context context,
	                    long mUpdateInterval,
	                    long mFastestUpdateInterval,
	                    int mRequestPriority) {
		this.mUpdateInterval = mUpdateInterval;
		this.mFastestUpdateInterval = mFastestUpdateInterval;
		this.mRequestPriority = mRequestPriority;
		this.context = new WeakReference<>(context);
		init();
	}

	private void init() {
		mRequestingLocationUpdates = false;
		mGoogleApiClient = null;
		mLocationRequest = null;
		mCurrentLocation = null;
		buildGoogleApiClient();
	}


	///////////////////////////////////////////////////////////////////////////
	// Client
	///////////////////////////////////////////////////////////////////////////

	private void buildGoogleApiClient() {
		Timber.d("Building google api client");
		mGoogleApiClient = new GoogleApiClient.Builder(context.get())
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
		createLocationRequest();
	}

	private void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(mUpdateInterval);
		mLocationRequest.setFastestInterval(mFastestUpdateInterval);
		mLocationRequest.setPriority(mRequestPriority);
	}


	///////////////////////////////////////////////////////////////////////////
	// Functionality
	///////////////////////////////////////////////////////////////////////////

	@RequiresPermission(ACCESS_COARSE_LOCATION)
	public boolean startLocationUpdates() {
		if (mRequestingLocationUpdates) {
			Timber.i("Already requesting location updates. Ignoring request.");
			return true;
		}
		if (!mGoogleApiClient.isConnected()) {
			Timber.i("Google client not connected. Ignoring request.");
			return false;
		}

		try {
			LocationServices
					.FusedLocationApi
					.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
			mRequestingLocationUpdates = true;
			Timber.d("Started location updates.");
		} catch (SecurityException se) {
			Timber.w(se, se.getMessage());
			mRequestingLocationUpdates = false;
		}
		return mRequestingLocationUpdates;
	}

	public void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
		mRequestingLocationUpdates = false;
		Timber.d("Stopped location updates.");
	}

	public void connect() {
		setState(STATE_CONNECTING);
		mGoogleApiClient.connect();
	}

	public void disconnect() {
		setState(STATE_DISCONNECTING);
		mGoogleApiClient.disconnect();
		setState(STATE_DISCONNECTED);
	}

	///////////////////////////////////////////////////////////////////////////
	// Getters - Setters
	///////////////////////////////////////////////////////////////////////////

	public Observable<Location> getLocationUpdates() {
		return locationSubject;
	}

	public Observable<String> getStateUpdates() {
		return stateSubject;
	}

	private void setState(@NonNull @State String state) {
		this.state = state;
		stateSubject.onNext(state);
	}

	@State
	public String getState() {
		return state;
	}

	///////////////////////////////////////////////////////////////////////////
	// Implemented
	///////////////////////////////////////////////////////////////////////////

	public void onLocationChanged(Location location) {
		mCurrentLocation = location;
		locationSubject.onNext(mCurrentLocation);
	}

	public void onConnected(@Nullable Bundle bundle) {
		setState(STATE_CONNECTED);
	}

	public void onConnectionSuspended(int i) {
		setState(STATE_CONNECTING);
	}

	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		setState(STATE_DISCONNECTED);
	}

	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
	public void onActivityStarted(Activity activity) {}
	public void onActivityStopped(Activity activity) {}
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
	public void onActivityDestroyed(Activity activity) {}

	public void onActivityResumed(Activity activity) {
		if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
			return;
		startLocationUpdates();
	}

	public void onActivityPaused(Activity activity) {
		stopLocationUpdates();
	}


	///////////////////////////////////////////////////////////////////////////
	// Inner classes
	///////////////////////////////////////////////////////////////////////////

	@Retention(SOURCE)
	@StringDef({STATE_CONNECTED, STATE_CONNECTING, STATE_DISCONNECTED, STATE_DISCONNECTING})
	public @interface State {}
}
