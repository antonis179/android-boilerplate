package org.amoustakos.boilerplate.examples.usecases.listing

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_listing.*
import org.amoustakos.boilerplate.R
import org.amoustakos.boilerplate.examples.usecases.listing.adapters.ActivityListingAdapter
import org.amoustakos.boilerplate.examples.usecases.listing.adapters.models.ActivityListingModel
import org.amoustakos.boilerplate.ui.activities.BaseActivity
import org.amoustakos.boilerplate.view.toolbars.BasicToolbar
import java.util.*

/**
 * Activity that lists all the activities in the same package and creates
 * a [RecyclerView] that starts each one on click.
 */
class ListingActivity : BaseActivity(), ActivityListingView {

	private var isDoubleBackToExitPressedOnce = false
	private var presenter: ActivityListingPresenter? = null

	private lateinit var adapter: ActivityListingAdapter

	private val toolbar = BasicToolbar(R.id.toolbar)


	@LayoutRes override fun layoutId() = R.layout.activity_listing

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activityComponent().inject(this)

		setupToolbar()

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

	private fun setupToolbar() {
		setupViewComponent(toolbar)
		toolbar.setTitle(R.string.title_activity_listing)
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
		adapter.clean()
		adapter.add(items)
		adapter.notifyDataSetChanged()
	}

	// =========================================================================================
	// Setup
	// =========================================================================================

	private fun setupRecycler() {
		if (!::adapter.isInitialized)
			adapter = ActivityListingAdapter(ArrayList())
		rv_activity_pool.adapter = adapter
	}

}