package org.amoustakos.utils.device

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.support.annotation.NonNull
import android.support.annotation.RequiresPermission
import android.support.v4.app.ActivityCompat
import com.google.android.gms.location.*
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.lang.ref.WeakReference


//TODO: Add lifecycle observer
class LocationUtil : LocationCallback, Application.ActivityLifecycleCallbacks {

	// Defaults
	var mUpdateInterval: Long = 120000
	var mFastestUpdateInterval = mUpdateInterval / 2
	var mRequestPriority = LocationRequest.PRIORITY_LOW_POWER

	//Publishers
	val locationSubject = PublishSubject.create<Location>()
	val stateSubject = PublishSubject.create<String>()

	//Client
	private var mLocationRequest: LocationRequest? = null
	private var mFusedLocationClient: FusedLocationProviderClient? = null

	//State
	var mRequestingLocationUpdates: Boolean = false
	@get:Synchronized private var mCurrentLocation: Location? = null


	//Internal variables
	private val context: WeakReference<Context>


	// =========================================================================================
	// Constructors
	// =========================================================================================

	@Throws(NullPointerException::class) constructor(@NonNull context: Context) {
		this.context = WeakReference(context)
		init()
	}

	@Throws(NullPointerException::class) constructor(context: Context,
													 mUpdateInterval: Long,
													 mFastestUpdateInterval: Long,
													 mRequestPriority: Int) {
		this.mUpdateInterval = mUpdateInterval
		this.mFastestUpdateInterval = mFastestUpdateInterval
		this.mRequestPriority = mRequestPriority
		this.context = WeakReference(context)
		init()
	}

	@Throws(NullPointerException::class) private fun init() {
		mRequestingLocationUpdates = false
		mLocationRequest = null
		mCurrentLocation = null
		mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context.get()!!)

		if (mFusedLocationClient == null)
			throw NullPointerException("Could not instantiate fused location client")

		createLocationRequest()
	}


	// =========================================================================================
	// Client
	// =========================================================================================

	private fun createLocationRequest() {
		mLocationRequest = LocationRequest()
		mLocationRequest!!.interval = mUpdateInterval
		mLocationRequest!!.fastestInterval = mFastestUpdateInterval
		mLocationRequest!!.priority = mRequestPriority
	}


	// =========================================================================================
	// Functionality
	// =========================================================================================

	@SuppressLint("MissingPermission")
	@RequiresPermission(value = ACCESS_COARSE_LOCATION)
	fun startLocationUpdates(): Boolean {
		if (mRequestingLocationUpdates) {
			Timber.i("Already requesting location updates. Ignoring request.")
			return true
		}

		try {
			mFusedLocationClient!!.requestLocationUpdates(mLocationRequest, this, Looper.myLooper())
			mRequestingLocationUpdates = true
			Timber.d("Started location updates.")
		} catch (se: Exception) {
			Timber.w(se, se.message)
			mRequestingLocationUpdates = false
		}

		return mRequestingLocationUpdates
	}

	fun stopLocationUpdates() {
		mFusedLocationClient!!.removeLocationUpdates(this)
		mRequestingLocationUpdates = false
		Timber.d("Stopped location updates.")
	}


	// =========================================================================================
	// Location updates
	// =========================================================================================

	override fun onLocationResult(p0: LocationResult?) {
		super.onLocationResult(p0)

		mCurrentLocation = p0?.lastLocation
		if (mCurrentLocation != null)
			locationSubject.onNext(mCurrentLocation!!)
	}

	override fun onLocationAvailability(p0: LocationAvailability?) {
		super.onLocationAvailability(p0)
		//TODO
	}

	// =========================================================================================
	// Lifecycle
	// =========================================================================================

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

}
