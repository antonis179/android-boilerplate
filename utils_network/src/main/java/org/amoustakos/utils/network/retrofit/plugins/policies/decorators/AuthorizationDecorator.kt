package org.amoustakos.utils.network.retrofit.plugins.policies.decorators

import io.reactivex.Observable
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.IDecoratorPolicy

class AuthorizationDecorator(
		private val isAuthorized: () -> Boolean,
		private val isAuthError: (t: Throwable) -> Boolean,
		private val authorize: () -> Observable<*>
) : IDecoratorPolicy {


	override fun <Model: Any> wrap(obs: Observable<Model>): Observable<Model> {
		return if (isAuthorized()) {
			obs.onErrorResumeNext { throwable: Throwable ->
				if (isAuthError(throwable))
					authorize().flatMap { obs }
				else
					Observable.error<Model>(throwable)
			}
		} else {
			authorize().flatMap { obs }
		}
	}

}