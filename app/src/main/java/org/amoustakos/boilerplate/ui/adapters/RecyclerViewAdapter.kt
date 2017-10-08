package org.amoustakos.boilerplate.ui.adapters

import android.support.v7.widget.RecyclerView


abstract class RecyclerViewAdapter<T: RecyclerView.ViewHolder, Y> : RecyclerView.Adapter<T>() {
    var items: List<Y>? = null



    fun getItem(position: Int): Y? = items?.get(position)

    override fun getItemCount(): Int {
        if (items == null)
            throw NullPointerException("No items in the adapter.")
        return items!!.size
    }
}