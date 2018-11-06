package org.amoustakos.boilerplate.examples.io.remote

import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import org.amoustakos.boilerplate.injection.annotations.network.DefaultRetrofitEngine
import org.amoustakos.boilerplate.injection.annotations.network.IDefaultDecoratorPolicyApplier
import org.amoustakos.boilerplate.injection.annotations.network.IDefaultPostPolicyApplier
import org.amoustakos.boilerplate.injection.annotations.network.IDefaultPrePolicyApplier
import org.amoustakos.utils.network.http.Headers.HEADER_CONTENT_TYPE
import org.amoustakos.utils.network.http.Headers.MIME_JSON
import org.amoustakos.utils.network.http.HttpStatusCode
import org.amoustakos.utils.network.http.models.NetResult
import org.amoustakos.utils.network.retrofit.RetrofitFactory
import org.amoustakos.utils.network.retrofit.plugins.base.ICall
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.IDecoratorPolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.IPostPolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.IPrePolicyApplier
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import javax.inject.Inject

class ExampleCall @Inject constructor(
		@DefaultRetrofitEngine
		private val engine: Retrofit,
		@IDefaultPrePolicyApplier
		override val prePoliciesApplier: IPrePolicyApplier,
		@IDefaultPostPolicyApplier
		override val postPoliciesApplier: IPostPolicyApplier,
		@IDefaultDecoratorPolicyApplier
		override val decoratorPoliciesApplier: IDecoratorPolicyApplier
) :
		ICall.NoRequest<JsonObject, JsonObject>
{

	private var service: ApiService? = null

	override val serviceCall                       get() = service!!::call
	override val observeScheduler   : Scheduler?   get() = null
	override val subscribeScheduler : Scheduler?   get() = null


	override fun initialize(): Observable<Boolean> {
		return Observable.fromCallable {
				if (service == null)
					service = RetrofitFactory.newService(engine, ApiService::class.java)
				true
			}
			.observeOn(Schedulers.computation())
			.onErrorReturn { false }
	}


	override fun map(): Function<Response<JsonObject>, NetResult<JsonObject>> {
		return Function { resp ->
			//Check code
			if (!HttpStatusCode.isSuccess(resp.code()))
				return@Function NetResult.Error(
						Exception("Response code was ${resp.code()}"))

			//Check contents
			return@Function resp.body()?.let { NetResult.Success(it) } ?: NetResult.Error(
					Exception("Error processing response body: \n\t${resp.body()}"))
		}
	}



	interface ApiService {
		@GET("/todos/1")
		@Headers("$HEADER_CONTENT_TYPE: $MIME_JSON")
		fun call(): Observable<Response<JsonObject>>
	}
}