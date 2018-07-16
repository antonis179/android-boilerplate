package org.amoustakos.boilerplate.examples.ui.contracts

import org.amoustakos.boilerplate.examples.view.models.ActivityListingModel
import org.amoustakos.boilerplate.ui.contracts.base.BaseContractActions
import org.amoustakos.boilerplate.ui.contracts.base.BaseContractView


interface ActivityListingView : BaseContractView {
	fun onItemsCollected(items: List<ActivityListingModel>)
}


interface ActivityListingActions : BaseContractActions {
	fun load()
}
