package org.amoustakos.boilerplate.ui.adapters.base

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter

import io.realm.RealmBaseAdapter
import io.realm.RealmModel


//TODO: test
abstract class RealmRecyclerViewAdapter<T : RealmModel> : Adapter<RecyclerView.ViewHolder>() {

	var realmAdapter: RealmBaseAdapter<T>? = null

	fun getItem(position: Int): T? = realmAdapter?.getItem(position)
}
