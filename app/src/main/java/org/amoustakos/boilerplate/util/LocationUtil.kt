package org.amoustakos.boilerplate.util

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.RequiresPermission
import android.support.annotation.StringDef
import android.support.v4.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import io.reactivex.subjects.PublishSubject
import org.amoustakos.boilerplate.injection.annotations.context.ApplicationContext
import timber.log.Timber
import java.lang.ref.WeakReference

//TODO: Add lifecycle observable
class LocationUtil :
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,
		LocationListener,
		Application.ActivityLifecycleCallbacks {

	companion object {
		const val STATE_CONNECTED = "CONNECTED"
		const val STATE_CONNECTING = "CONNECTING"
		const val STATE_DISCONNECTED = "DISCONNECTED"
		const val STATE_DISCONNECTING = "DISCONNECTING"
	}


	// Defaults
	var mUpdateInterval: Long = 120000
	var mFastestUpdateInterval = mUpdateInterval / 2
	var mRequestPriority = LocationRequest.PRIORITY_LOW_POWER

	//Publishers
	val locationSubject = PublishSubject.create<Location>()
	val stateSubject = PublishSubject.create<String>()

	//Client
	private var mGoogleApiClient: GoogleApiClient? = null
	private var mLocationRequest: LocationRequest? = null

	//State
	var mRequestingLocationUpdates: Boolean = false
	@State @get:Synchronized @set:Synchronized var state = STATE_DISCONNECTED
	set(@State value) {
		state = value
		stateSubject.onNext(state)
	}
	private var mCurrentLocation: Location? = null


	//Internal variables
	private val context: WeakReference<Context>


	// =========================================================================================
	// Constructors
	// =========================================================================================

	constructor(@ApplicationContext @NonNull context: Context) {
		this.context = WeakReference(context)
		init()
	}

	constructor(@ApplicationContext context: Context,
	            mUpdateInterval: Long,
	            mFastestUpdateInterval: Long,
	            mRequestPriority: Int) {
		this.mUpdateInterval = mUpdateInterval
		this.mFastestUpdateInterval = mFastestUpdateInterval
		this.mRequestPriority = mRequestPriority
		this.context = WeakReference(context)
		init()
	}

	private fun init() {
		mRequestingLocationUpdates = false
		mGoogleApiClient = null
		mLocationRequest = null
		mCurrentLocation = null
		buildGoogleApiClient()
	}


	// =========================================================================================
	// Client
	// =========================================================================================

	private fun buildGoogleApiClient() {
		Timber.d("Building google api client")

		if (context.get() == null)
			throw NullPointerException("Location provider tried to initialize with null context.")

		mGoogleApiClient = GoogleApiClient.Builder(context.get()!!)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build()
		createLocationRequest()
	}

	private fun createLocationRequest() {
		mLocationRequest = LocationRequest()
		mLocationRequest!!.interval = mUpdateInterval
		mLocationRequest!!.fastestInterval = mFastestUpdateInterval
		mLocationRequest!!.priority = mRequestPriority
	}


	// =========================================================================================
	// Functionality
	// =========================================================================================

	@RequiresPermission(value = ACCESS_COARSE_LOCATION)
	fun startLocationUpdates(): Boolean {
		if (mRequestingLocationUpdates) {
			Timber.i("Already requesting location updates. Ignoring request.")
			return true
		}
		if (!mGoogleApiClient!!.isConnected) {
			Timber.i("Google client not connected. Ignoring request.")
			return false
		}

		try {
			LocationServices
					.FusedLocationApi
					.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
			mRequestingLocationUpdates = true
			Timber.d("Started location updates.")
		} catch (se: SecurityException) {
			Timber.w(se, se.message)
			mRequestingLocationUpdates = false
		}

		return mRequestingLocationUpdates
	}

	fun stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
		mRequestingLocationUpdates = false
		Timber.d("Stopped location updates.")
	}

	fun connect() {
		state = STATE_CONNECTING
		mGoogleApiClient!!.connect()
	}

	fun disconnect() {
		state = STATE_DISCONNECTING
		mGoogleApiClient!!.disconnect()
		state = STATE_DISCONNECTED
	}


	// =========================================================================================
	// Implemented
	// =========================================================================================

	override fun onLocationChanged(location: Location) {
		mCurrentLocation = location
		locationSubject.onNext(mCurrentLocation)
	}

	override fun onConnected(bundle: Bundle?) {
		state = STATE_CONNECTED
	}

	override fun onConnectionSuspended(i: Int) {
		state = STATE_CONNECTING
	}

	override fun onConnectionFailed(connectionResult: ConnectionResult) {
		state = STATE_DISCONNECTED
	}

	override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {}
	override fun onActivityStarted(activity: Activity) {}
	override fun onActivityStopped(activity: Activity) {}
	override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
	override fun onActivityDestroyed(activity: Activity) {}

	override fun onActivityResumed(activity: Activity) {
		if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
			return
		startLocationUpdates()
	}

	override fun onActivityPaused(activity: Activity) = stopLocationUpdates()


	// =========================================================================================
	// Inner classes
	// =========================================================================================

	@Retention(AnnotationRetention.SOURCE)
	@StringDef(STATE_CONNECTED, STATE_CONNECTING, STATE_DISCONNECTED, STATE_DISCONNECTING)
	annotation class State
}
