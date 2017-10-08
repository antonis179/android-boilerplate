package org.amoustakos.boilerplate.ui.adapters.base

import android.view.View
import android.view.ViewGroup

import io.realm.RealmBaseAdapter
import io.realm.RealmModel
import io.realm.RealmResults


class RealmModelAdapter<T : RealmModel>(realmResults: RealmResults<T>) : RealmBaseAdapter<T>(realmResults) {
	override fun getView(position: Int, convertView: View, parent: ViewGroup): View? = null
}