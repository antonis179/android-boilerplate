package org.amoustakos.boilerplate.examples.usecases.listing.models

import android.content.Context
import android.view.ViewGroup
import org.amoustakos.boilerplate.examples.usecases.listing.holders.ActivityListingViewHolder
import org.amoustakos.boilerplate.ui.activities.BaseActivity
import org.amoustakos.boilerplate.view.models.base.BaseViewModel
import org.amoustakos.boilerplate.view.models.base.PublisherItem


data class ActivityListingModel (
    val activity:Class<out BaseActivity>?,
    val name:String? = null,
    val description:String? = null
): BaseViewModel<ActivityListingViewHolder, ActivityListingModel> {

    override fun makeHolder(
            ctx: Context,
            view: ViewGroup,
            publishers: List<PublisherItem<ActivityListingModel>>
    ) = ActivityListingViewHolder(view, publishers)

}
