package org.amoustakos.boilerplate.io.local.migrations.base

import io.realm.DynamicRealm
import io.realm.RealmMigration

/**
 * Created by antonis on 02/09/2017.
 */

class Migration : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema
    }


}
