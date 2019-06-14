package org.amoustakos.utils.network.retrofit.plugins.base

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.Function
import org.amoustakos.utils.network.http.models.NetResult
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.DecoratorPolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.PostPolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.PrePolicyApplier
import retrofit2.Response

interface Call {

	val observeScheduler: Scheduler?
	val subscribeScheduler: Scheduler?

	val prePoliciesApplier: PrePolicyApplier?
	val postPoliciesApplier: PostPolicyApplier?
	val decoratorPoliciesApplier: DecoratorPolicyApplier?



	fun initialize(): Observable<Boolean>


	/**
	 * Interface to implement when making calls that don't require a request
	 */
	interface NoRequest<Model: Any, MappedModel: Any>: Call {

		val serviceCall: () -> Observable<Response<Model>>

		fun perform(): Observable<NetResult<MappedModel>> {
			val init = initialize()
			var call = initToServiceCall(init)

			observeScheduler            ?.let { call = call.observeOn(it) }
			subscribeScheduler          ?.let { call = call.subscribeOn(it) }
			decoratorPoliciesApplier    ?.let { call = it.apply(call) }
			prePoliciesApplier          ?.let { call = it.apply(call) }

			val mappedCall = call.map(map())

			return postPoliciesApplier?.apply(mappedCall) ?: mappedCall
		}

		fun initToServiceCall(init: Observable<Boolean>): Observable<Response<Model>> {
			return init.flatMap { success ->
				if (!success)
					return@flatMap Observable.error<Response<Model>>(
							RuntimeException("Initialization failed"))
				else
					return@flatMap serviceCall()
			}
		}

		fun map(): Function<Response<Model>, NetResult<MappedModel>>
	}


	/**
	 * Interface to implement when making calls that require a request
	 */
	interface WithRequest<Request: Any, Model: Any, MappedModel: Any>: Call {

		val serviceCall: (request: Request) -> Observable<Response<Model>>

		fun perform(request: Request): Observable<NetResult<MappedModel>> {
			val init = initialize()
			var call = initToServiceCall(init, request)

			observeScheduler            ?.let { call = call.observeOn(it) }
			subscribeScheduler          ?.let { call = call.subscribeOn(it) }
			decoratorPoliciesApplier    ?.let { call = it.apply(call) }
			prePoliciesApplier          ?.let { call = it.apply(call) }

			val mappedCall = call.map(map())

			return postPoliciesApplier?.apply(mappedCall) ?: mappedCall
		}

		fun initToServiceCall(
				init: Observable<Boolean>, request: Request
		): Observable<Response<Model>> {
			return init.flatMap { success ->
				if (!success)
					return@flatMap Observable.error<Response<Model>>(
							RuntimeException("Initialization failed"))
				else
					return@flatMap serviceCall(request)
			}
		}


		fun map(): Function<Response<Model>, NetResult<MappedModel>>
	}

}