package org.amoustakos.boilerplate.examples.usecases.listing

import org.amoustakos.boilerplate.examples.usecases.listing.adapters.models.ActivityListingModel
import org.amoustakos.boilerplate.ui.contracts.base.BaseViewContract


interface ActivityListingView : BaseViewContract {
	fun onItemsCollected(items: List<ActivityListingModel>)
}