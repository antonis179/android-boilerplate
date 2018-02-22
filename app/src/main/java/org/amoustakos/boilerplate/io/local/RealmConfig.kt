package org.amoustakos.boilerplate.io.local

import io.realm.RealmConfiguration
import org.amoustakos.boilerplate.io.local.migrations.base.Migration

object RealmConfig {

    private const val DEFAULT_VERSION = 0L


    @JvmStatic fun defaultConfig(): RealmConfiguration =
        RealmConfiguration.Builder()
            .schemaVersion(DEFAULT_VERSION)
            .migration(Migration())
            .build()

}