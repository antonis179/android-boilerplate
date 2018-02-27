package org.amoustakos.boilerplate.io

import io.realm.RealmConfiguration
import org.amoustakos.boilerplate.io.migrations.Migration

object RealmConfig {

    private const val DEFAULT_VERSION = 0L


    @JvmStatic fun defaultConfig(): RealmConfiguration =
        RealmConfiguration.Builder()
            .schemaVersion(DEFAULT_VERSION)
            .migration(Migration())
            .build()

}