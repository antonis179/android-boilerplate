package org.amoustakos.boilerplate.examples.ui.contracts

import org.amoustakos.boilerplate.examples.io.local.models.ActivityListingModel
import org.amoustakos.cryptoranker.ui.contracts.base.BaseContractActions
import org.amoustakos.cryptoranker.ui.contracts.base.BaseContractView


interface ActivityListingView : BaseContractView {
	fun onItemsCollected(items: List<ActivityListingModel>)
}


interface ActivityListingActions : BaseContractActions {
	fun load()
}
