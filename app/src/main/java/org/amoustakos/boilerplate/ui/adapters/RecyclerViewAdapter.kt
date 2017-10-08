package org.amoustakos.boilerplate.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup


abstract class RecyclerViewAdapter<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {
    var items: List<T>? = null




    override fun onBindViewHolder(holder: T, position: Int) {
        TODO("not implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): T {
        TODO("not implemented")
    }


    fun getItem(position: Int): T? = items?.get(position)

    override fun getItemCount(): Int {
        if (items == null)
            throw NullPointerException("No items in the adapter.")
        return items!!.size
    }
}