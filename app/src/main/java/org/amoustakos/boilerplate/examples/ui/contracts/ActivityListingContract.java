package org.amoustakos.boilerplate.examples.ui.contracts;

import org.amoustakos.boilerplate.examples.io.local.models.ActivityListingModel;

import java.util.List;

/**
 * Created by Antonis Moustakos on 10/8/2017.
 */

public class ActivityListingContract {


	public interface View extends BaseContractView {
		void onItemsCollected(List<ActivityListingModel> items);
	}


	public interface Actions extends BaseContractActions {
		void load();
	}


}
