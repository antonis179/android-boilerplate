package org.amoustakos.boilerplate.examples.io.remote

import io.reactivex.Observable
import org.amoustakos.boilerplate.io.remote.BaseCall
import org.amoustakos.models.network.NetEvent
import org.amoustakos.utils.network.Headers.HEADER_CONTENT_TYPE
import org.amoustakos.utils.network.Headers.MIME_JSON
import org.amoustakos.utils.network.HttpStatusCode
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject


/**
 * Created by antonis on 17/01/2018.
 */

class ExampleNetCall
@Inject
constructor(private val service: ApiService) : BaseCall() {

    companion object {
        private const val RETRIES = 3
    }

    // =========================================================================================
    // Implementation
    // =========================================================================================


    fun post(): Observable<NetEvent<JSONObject>> {
        return service.makeCall()
                .compose(applyTransformations(RETRIES))
                .compose(applyErrorLogging())
                .map { resp ->
                    var hasError = false

                    //Check code
                    if (!HttpStatusCode.isSuccess(resp.code()))
                        hasError = true

                    //Check contents
                    if (resp.body() == null)
                        hasError = true

                    NetEvent(
                            hasError = hasError,
                            item = resp.body())
                }
                .compose(applyMapErrorResponse())
    }


    // =========================================================================================
    // Inner classes
    // =========================================================================================

    interface ApiService {
        @Headers(HEADER_CONTENT_TYPE + " " + MIME_JSON)
        @POST("/endpoint")
        fun makeCall(): Observable<Response<JSONObject>>
    }



}
