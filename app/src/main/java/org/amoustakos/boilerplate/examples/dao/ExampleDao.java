package org.amoustakos.boilerplate.examples.dao;

import org.amoustakos.boilerplate.examples.models.ExampleModel;
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealm;
import org.amoustakos.boilerplate.io.db.dao.base.BaseDao;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

import static org.amoustakos.boilerplate.examples.models.ExampleModel.COL_ID;
import static org.amoustakos.boilerplate.examples.models.ExampleModel.COL_NAME;


/**
 * TODO
 */
public class ExampleDao extends BaseDao<ExampleModel> {




	/*
	 * Constructors
	 */
	@Inject
	public ExampleDao(@DefaultRealm Realm realm) {
		super(realm, ExampleModel.class);
	}



	/*
	 * CRUD
	 */

	public ExampleModel getByID(String id) {
		return getOneByColumn(COL_ID, id);
	}


	public RealmResults<ExampleModel> getByName(String name) {
		return getByColumn(COL_NAME, name);
	}





}
