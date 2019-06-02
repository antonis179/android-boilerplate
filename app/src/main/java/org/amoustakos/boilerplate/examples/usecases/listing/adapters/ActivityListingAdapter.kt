package org.amoustakos.boilerplate.examples.usecases.listing.adapters

import android.view.ViewGroup
import org.amoustakos.boilerplate.examples.usecases.listing.holders.ActivityListingViewHolder
import org.amoustakos.boilerplate.examples.usecases.listing.models.ActivityListingModel
import org.amoustakos.boilerplate.view.adapters.base.RecyclerAdapter
import org.amoustakos.boilerplate.view.models.base.PublisherItem

class ActivityListingAdapter(
        mItems: MutableList<ActivityListingModel>,
        publishers: List<PublisherItem<ActivityListingModel>> = ArrayList()
): RecyclerAdapter<ActivityListingViewHolder, ActivityListingModel>(mItems, publishers) {


    override fun makeHolder(
            parent: ViewGroup,
            publishers: List<PublisherItem<ActivityListingModel>>
    ): ActivityListingViewHolder {
        return ActivityListingViewHolder(parent, publishers)
    }


}