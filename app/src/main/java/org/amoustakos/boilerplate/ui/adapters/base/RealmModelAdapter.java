package org.amoustakos.boilerplate.ui.adapters.base;

import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmBaseAdapter;
import io.realm.RealmModel;
import io.realm.RealmResults;


public class RealmModelAdapter<T extends RealmModel> extends RealmBaseAdapter<T> {

    public RealmModelAdapter(RealmResults<T> realmResults) {
        super(realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}