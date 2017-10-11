package org.amoustakos.boilerplate.ui.adapters

import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView


abstract class RecyclerViewAdapter<T: RecyclerView.ViewHolder, Y>(@NonNull protected var items: List<Y>) :
        RecyclerView.Adapter<T>() {


    fun getItem(position: Int): Y? = items[position]
    override fun getItemCount(): Int = items.size

}