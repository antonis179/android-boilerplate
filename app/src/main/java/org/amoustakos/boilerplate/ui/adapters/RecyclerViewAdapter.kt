package org.amoustakos.boilerplate.ui.adapters

import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView


abstract class RecyclerViewAdapter<T : RecyclerView.ViewHolder, Y>
    (@NonNull protected var mItems: MutableList<Y>) :
        RecyclerView.Adapter<T>() {

    private val mLock = Any()


    fun getItem(position: Int): Y? = mItems[position]
    override fun getItemCount(): Int = mItems.size


    fun clean() {
        synchronized(mLock) {
            mItems.clear()
        }
    }

    fun addAll(items: List<Y>) {
        synchronized(mLock) {
            this.mItems = ArrayList(items)
        }
    }

    fun addAll(vararg items: Y) {
        synchronized(mLock) {
            for (model in items)
                this.mItems.add(model)
        }
    }

    fun add(item: Y) {
        synchronized(mLock) {
            this.mItems.add(item)
        }
    }

}