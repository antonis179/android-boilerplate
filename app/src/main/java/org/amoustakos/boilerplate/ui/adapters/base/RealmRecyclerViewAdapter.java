package org.amoustakos.boilerplate.ui.adapters.base;

import android.support.v7.widget.RecyclerView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmModel;

public abstract class RealmRecyclerViewAdapter<T extends RealmModel> extends RecyclerView.Adapter {
    private RealmBaseAdapter<T> realmBaseAdapter;

    public T getItem(int position) {
        return realmBaseAdapter.getItem(position);
    }

    public RealmBaseAdapter<T> getRealmAdapter() {
        return realmBaseAdapter;
    }

    public void setRealmAdapter(RealmBaseAdapter<T> realmAdapter) {
        realmBaseAdapter = realmAdapter;
    }
}
