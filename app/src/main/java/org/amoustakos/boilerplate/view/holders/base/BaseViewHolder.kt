package org.amoustakos.boilerplate.view.holders.base

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import butterknife.OnLongClick
import org.amoustakos.boilerplate.view.models.base.ClickEvent
import org.amoustakos.boilerplate.view.models.base.PublisherItem
import org.amoustakos.boilerplate.view.models.base.Type
import org.amoustakos.boilerplate.view.models.base.Type.LONG_CLICK

abstract class BaseViewHolder <Model> (
        itemView: View,
        protected val publishers: List<PublisherItem<Model>>
) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    protected var mItem: Model? = null


    open fun loadItem(item: Model) {
        mItem = item
    }

    @OnClick
    open fun onClick() =
            publishers
                    .filter {
                        it.type == Type.CLICK
                    }
                    .forEach {
                        it.publisher.onNext(ClickEvent(mItem!!))
                    }

    @OnLongClick
    open fun onLongClick(): Boolean {
        publishers
                .filter {
                    it.type == LONG_CLICK
                }
                .forEach {
                    it.publisher.onNext(ClickEvent(mItem!!))
                }
        return true
    }


    companion object {
        @JvmStatic
        fun makeView(parent: ViewGroup, viewType: Int, attach: Boolean) =
                LayoutInflater.from(parent.context).inflate(viewType, parent, attach)

        @JvmStatic
        fun makeView(parent: ViewGroup, viewType: Int) = makeView(parent, viewType, false)
    }

}