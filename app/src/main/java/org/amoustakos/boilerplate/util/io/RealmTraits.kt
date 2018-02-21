package org.amoustakos.boilerplate.util.io

import io.realm.Realm
import io.realm.RealmModel

/**
 * Created by Antonis Moustakos on 2/21/18.
 */
open class RealmTraits {

    infix fun Realm.transWithResp(transaction: () -> RealmModel?): RealmModel? {
        beginTransaction()
        val resp = transaction.invoke()
        commitTransaction()
        return resp
    }

    infix fun Realm.trans(transaction: () -> Unit) {
        this.beginTransaction()
        transaction.invoke()
        this.commitTransaction()
    }

}