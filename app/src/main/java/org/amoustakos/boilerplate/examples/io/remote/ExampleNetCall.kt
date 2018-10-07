package org.amoustakos.boilerplate.examples.io.remote

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.amoustakos.boilerplate.net.BaseCall
import org.amoustakos.models.network.NetResult
import org.amoustakos.utils.network.Headers.HEADER_CONTENT_TYPE
import org.amoustakos.utils.network.Headers.MIME_JSON
import org.amoustakos.utils.network.HttpStatusCode
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject


class ExampleNetCall @Inject constructor(
		private val service: ApiService
) : BaseCall() {


	fun call(): Observable<NetResult<JSONObject>> {
        return service.makeCall()
                .observeOn(Schedulers.io())
                .compose(applyTransformations(RETRIES))
                .compose(applyErrorLogging())
                .map { resp ->
                    //Check code
                    if (!HttpStatusCode.isSuccess(resp.code()))
                        return@map NetResult.Error(
		                        Exception("Response code was ${resp.code()}"))

                    //Check contents
	                return@map resp.body()?.let { NetResult.Success(it) } ?:
		                NetResult.Error(
				                Exception("Error processing response body: \n\t${resp.body()}"))
                }
                .compose(applyMapErrorResponse())
    }


    interface ApiService {
        @POST("/endpoint")
        @Headers("$HEADER_CONTENT_TYPE $MIME_JSON")
        fun makeCall(): Observable<Response<JSONObject>>
    }


}
