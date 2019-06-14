package org.amoustakos.utils.network.retrofit.plugins.interfaces.base

import io.reactivex.Observable
import org.amoustakos.utils.network.http.models.NetResult
import retrofit2.Response


interface PolicyApplier



interface DecoratorPolicyApplier : PolicyApplier {

	fun decorators(): List<DecoratorPolicy>?

	fun <Model: Any> apply(observable: Observable<Model>): Observable<Model> {
		var returnObs: Observable<Model> = observable
		decorators()?.forEach {
			returnObs = it.wrap(returnObs)
		}
		return returnObs
	}

}

interface PostPolicyApplier : PolicyApplier {

	fun postPolicies(): List<PostPolicy>?

	fun <Model: Any> apply(observable: Observable<NetResult<Model>>): Observable<NetResult<Model>> {
		var returnObs: Observable<NetResult<Model>> = observable
		postPolicies()?.forEach {
			returnObs = returnObs.compose(it.apply())
		}
		return returnObs
	}

}


interface PrePolicyApplier : PolicyApplier {

	fun prePolicies(): List<PrePolicy>?

	fun <Model: Any> apply(observable: Observable<Response<Model>>): Observable<Response<Model>> {
		var returnObs: Observable<Response<Model>> = observable
		prePolicies()?.forEach {
			returnObs = returnObs.compose(it.apply())
		}
		return returnObs
	}

}