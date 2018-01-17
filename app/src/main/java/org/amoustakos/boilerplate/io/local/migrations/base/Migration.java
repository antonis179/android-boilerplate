package org.amoustakos.boilerplate.io.local.migrations.base;

import android.support.annotation.NonNull;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by antonis on 02/09/2017.
 */

public class Migration implements RealmMigration {

	@Override
	public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {
		RealmSchema schema = realm.getSchema();
	}



}
