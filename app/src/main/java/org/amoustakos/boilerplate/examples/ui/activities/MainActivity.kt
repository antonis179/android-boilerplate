package org.amoustakos.boilerplate.examples.ui.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.amoustakos.boilerplate.R
import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingActions
import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingView
import org.amoustakos.boilerplate.examples.ui.presenters.ActivityListingPresenter
import org.amoustakos.boilerplate.examples.view.adapters.ActivityListingAdapter
import org.amoustakos.boilerplate.examples.view.models.ActivityListingModel
import org.amoustakos.boilerplate.ui.activities.BaseActivity
import timber.log.Timber
import java.util.*

/**
 * Activity that lists all the activities in the same package and creates <br></br>
 * a [RecyclerView] that starts each one on click.
 */
//adb shell am start -a com.google.android.gms.actions.SEARCH_ACTION -e query flights org.amoustakos.boilerplate
//https://developers.google.com/voice-actions/system/#step_3_update_your_app_completion_status
class MainActivity : BaseActivity(), ActivityListingView {

	private var isDoubleBackToExitPressedOnce = false
	private var mPresenter: ActivityListingActions? = null

	private var exampleAdapter: ActivityListingAdapter? = null

	@LayoutRes override fun layoutId() = R.layout.activity_main


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		Timber.d(intent.action + " | " + intent.dataString)

		toolbar?.setTitle(R.string.title_activity_main)

		if (mPresenter == null) {
			mPresenter = ActivityListingPresenter(
					this,
					packageName,
					packageManager)
			mPresenter!!.subscribeToLifecycle(lifecycle)
		}

		setupRecycler()
		mPresenter!!.load()

		handleVoiceSearch(intent)
	}

	override fun onDestroy() {
		super.onDestroy()
		mPresenter?.unsubscribeFromLifecycle(lifecycle)
	}

	override fun onNewIntent(intent: Intent) {
		super.onNewIntent(intent)
		handleVoiceSearch(intent)
	}

	override fun onBackPressed() {
		if (isDoubleBackToExitPressedOnce) {
			super.onBackPressed()
			return
		}
		isDoubleBackToExitPressedOnce = true
		Toast.makeText(this, getString(R.string.toast_back_to_exit), Toast.LENGTH_SHORT).show()
		Handler().postDelayed({ isDoubleBackToExitPressedOnce = false }, 2000)
	}

	// =========================================================================================
	// Voice
	// =========================================================================================

	private fun handleVoiceSearch(intent: Intent?) {
		if (intent != null && ACTION_VOICE_SEARCH == intent.action) {
			val query = intent.getStringExtra(SearchManager.QUERY)
			//TODO
			Timber.i(intent.action)
			Timber.i(intent.dataString)
			Timber.i(query)
		}
	}

	// =========================================================================================
	// View interactions
	// =========================================================================================

	override fun onItemsCollected(items: List<ActivityListingModel>) {
		refreshAdapter(items)
	}

	private fun refreshAdapter(items: List<ActivityListingModel>) {
		exampleAdapter!!.clean()
		exampleAdapter!!.addAll(items)
		exampleAdapter!!.notifyDataSetChanged()
	}

	// =========================================================================================
	// Setup
	// =========================================================================================

	private fun setupRecycler() {
		if (exampleAdapter == null)
			exampleAdapter = ActivityListingAdapter(ArrayList())
		rv_activity_pool.adapter = exampleAdapter
	}


	override fun onNavigationItemSelected(item: MenuItem) = false

	companion object {
		private const val ACTION_VOICE_SEARCH = "com.google.android.gms.actions.SEARCH_ACTION"
	}
}
