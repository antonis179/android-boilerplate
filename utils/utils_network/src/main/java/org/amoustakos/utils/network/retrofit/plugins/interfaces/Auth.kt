package org.amoustakos.utils.network.retrofit.plugins.interfaces

import io.reactivex.Observable

interface Auth {

    fun isAuthorized(): Boolean

    fun authorize(): Observable<Boolean>

}
