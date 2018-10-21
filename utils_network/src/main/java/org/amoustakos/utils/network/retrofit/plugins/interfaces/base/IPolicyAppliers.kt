package org.amoustakos.utils.network.retrofit.plugins.interfaces.base

import io.reactivex.Observable
import org.amoustakos.utils.network.http.models.NetResult
import retrofit2.Response


interface IPolicyApplier



interface IDecoratorPolicyApplier : IPolicyApplier {

	fun decorators(): List<IDecoratorPolicy>?

	fun <Model: Any> apply(observable: Observable<Model>): Observable<Model> {
		var returnObs: Observable<Model> = observable
		decorators()?.forEach {
			returnObs = it.wrap(returnObs)
		}
		return returnObs
	}

}

interface IPostPolicyApplier : IPolicyApplier {

	fun postPolicies(): List<IPostPolicy>?

	fun <Model: Any> apply(observable: Observable<NetResult<Model>>): Observable<NetResult<Model>> {
		var returnObs: Observable<NetResult<Model>> = observable
		postPolicies()?.forEach {
			returnObs = returnObs.compose(it.apply())
		}
		return returnObs
	}

}


interface IPrePolicyApplier : IPolicyApplier {

	fun prePolicies(): List<IPrePolicy>?

	fun <Model: Any> apply(observable: Observable<Response<Model>>): Observable<Response<Model>> {
		var returnObs: Observable<Response<Model>> = observable
		prePolicies()?.forEach {
			returnObs = returnObs.compose(it.apply())
		}
		return returnObs
	}

}