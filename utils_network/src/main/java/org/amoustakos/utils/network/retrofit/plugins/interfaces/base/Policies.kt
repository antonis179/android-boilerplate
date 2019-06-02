package org.amoustakos.utils.network.retrofit.plugins.interfaces.base

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.amoustakos.utils.network.http.models.NetResult
import retrofit2.Response


interface Policy

/**
 * Pre-mapping policies.
 * These will run before your network model mapping function
 * if you have one - right after the call otherwise.
 */
interface PrePolicy: Policy {

	fun <Model: Any, Resp: Response<Model>> apply(): ObservableTransformer<Resp, Resp>

}


/**
 * Post-mapping policies.
 * These will run after your network model mapping function
 * if you have one - right after the pre-policies otherwise.
 */
interface PostPolicy: Policy {

	fun <Model: Any> apply(): ObservableTransformer<NetResult<Model>, NetResult<Model>>

}


/**
 * Policy that "wraps" a given observable.
 * A common use-case would be an authorization call that needs
 * to happen every time you receive a 401 code or a time out occurs.
 *
 * These policies are applied before any other policy.
 */
interface DecoratorPolicy: Policy {

	fun <Model: Any> wrap(obs: Observable<Model>): Observable<Model>

}