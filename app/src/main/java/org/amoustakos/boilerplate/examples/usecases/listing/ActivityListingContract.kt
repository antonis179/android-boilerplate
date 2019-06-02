package org.amoustakos.boilerplate.examples.usecases.listing

import org.amoustakos.boilerplate.examples.usecases.listing.models.ActivityListingModel
import org.amoustakos.boilerplate.ui.contracts.base.BaseContractActions
import org.amoustakos.boilerplate.ui.contracts.base.BaseContractView


interface ActivityListingView : BaseContractView {
	fun onItemsCollected(items: List<ActivityListingModel>)
}


interface ActivityListingActions : BaseContractActions {
	fun load()
}
