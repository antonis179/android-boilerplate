package org.amoustakos.boilerplate.examples.io.local.models;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;


@RealmClass
public class ExampleModel implements RealmModel{


	public static final String COL_ID = "id",
								COL_NAME = "name";


	public String id;
	public String name;



}
