package org.amoustakos.boilerplate.examples.ui.activities

import android.os.Bundle
import android.os.Handler
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_listing.*
import org.amoustakos.boilerplate.R
import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingActions
import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingView
import org.amoustakos.boilerplate.examples.ui.presenters.ActivityListingPresenter
import org.amoustakos.boilerplate.examples.view.adapters.ActivityListingAdapter
import org.amoustakos.boilerplate.examples.view.models.ActivityListingModel
import org.amoustakos.boilerplate.ui.activities.BaseActivity
import org.amoustakos.boilerplate.view.toolbars.BasicToolbar
import java.util.*

/**
 * Activity that lists all the activities in the same package and creates
 * a [RecyclerView] that starts each one on click.
 */
class ListingActivity : BaseActivity(), ActivityListingView {

	private var isDoubleBackToExitPressedOnce = false
	private var presenter: ActivityListingActions? = null

	private var adapter: ActivityListingAdapter? = null

	private val toolbar = BasicToolbar(R.id.toolbar)

	@LayoutRes override fun layoutId() = R.layout.activity_listing


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activityComponent().inject(this)

		setupViewComponent(toolbar)
		toolbar.setTitle(R.string.title_activity_listing)
		toolbar.setAsActionbar(this)

		if (presenter == null) {
			presenter = ActivityListingPresenter(
					this,
					packageName,
					packageManager,
					lifecycle
			)
		}

		setupRecycler()
		presenter!!.load()
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
	// View interactions
	// =========================================================================================

	override fun onItemsCollected(items: List<ActivityListingModel>) {
		refreshAdapter(items)
	}

	private fun refreshAdapter(items: List<ActivityListingModel>) {
		adapter!!.clean()
		adapter!!.add(items)
		adapter!!.notifyDataSetChanged()
	}

	// =========================================================================================
	// Setup
	// =========================================================================================

	private fun setupRecycler() {
		if (adapter == null)
			adapter = ActivityListingAdapter(ArrayList())
		rv_activity_pool.adapter = adapter
	}

}