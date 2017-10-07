package org.amoustakos.boilerplate.examples.io.local.models

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
data class ExampleModel (
                                var id: String? = null,
                                var name: String? = null
                        ) : RealmModel {

    companion object Column {
        val ID = "id"
        val NAME = "name"
    }


}