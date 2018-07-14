package org.amoustakos.boilerplate.examples.ui.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.amoustakos.boilerplate.R;
import org.amoustakos.boilerplate.examples.io.local.models.ActivityListingModel;
import org.amoustakos.boilerplate.view.adapters.RecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ActivityListingAdapter extends RecyclerViewAdapter<ActivityListingAdapter.ActivityListingViewHolder, ActivityListingModel> {
    private final PublishSubject<ActivityListingModel> onClickSubject = PublishSubject.create();
    private final PublishSubject<ActivityListingModel> onLongClickSubject = PublishSubject.create();


	// =========================================================================================
	// Constructors
	// =========================================================================================

	public ActivityListingAdapter(List<ActivityListingModel> items) {
		super(items);
    }


	// =========================================================================================
	// Overrides
	// =========================================================================================

	@Override
	public ActivityListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_activity_description, parent, false);
        return new ActivityListingViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ActivityListingViewHolder holder, int position) {
		holder.loadItem(getItem(position));
	}


	// =========================================================================================
	// Observers
	// =========================================================================================

    public Observable<ActivityListingModel> getPositionClicks(){
        return onClickSubject;
    }

    public Observable<ActivityListingModel> getLongClicks(){
        return onLongClickSubject;
    }


	// =========================================================================================
	// Inner classes
	// =========================================================================================

    public class ActivityListingViewHolder extends RecyclerView.ViewHolder {

	    @BindView(R.id.card_view_container)
	    CardView container;
	    @BindView(R.id.tv_activity_name)
	    TextView name;
	    @BindView(R.id.tv_activity_descr)
	    TextView descr;

	    ActivityListingModel item;

        public ActivityListingViewHolder(View itemView) {
            super(itemView);
            initViews();
        }

        private void initViews(){
	        ButterKnife.bind(this, itemView);
	        container.setOnClickListener(v->onClickSubject.onNext(item));
	        container.setOnLongClickListener(v -> {
		        onLongClickSubject.onNext(item);
		        return true;
	        });
        }

        private void loadItem(ActivityListingModel mdl){
            item = mdl;
            name.setText(mdl.getName());
            descr.setText(mdl.getDescription());
        }
    }
}
