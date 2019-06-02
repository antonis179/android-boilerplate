package org.amoustakos.utils.network.retrofit.plugins.policies.pre

import io.reactivex.ObservableTransformer
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.amoustakos.utils.network.http.Headers
import org.amoustakos.utils.network.retrofit.plugins.interfaces.policies.pre.ErrorReturnPolicy
import retrofit2.HttpException
import retrofit2.Response



@Suppress("UNCHECKED_CAST")
class DefaultJsonErrorReturnPolicy: ErrorReturnPolicy {

	private val emptyBody = "{}".intern()


	override fun <Model: Any, Resp: Response<Model>> apply(): ObservableTransformer<Resp, Resp> {
		return ObservableTransformer {
			it.onErrorReturn { t ->
				if (t is HttpException)
					t.response() as Resp
				else
					makeErrorJsonResponse<Model, Resp>() as Resp
			}
		}
	}


	private fun <Model, Resp: Response<Model>> makeErrorJsonResponse(): Response<Resp> =
			Response.error(500, emptyJSONResponseBody())

	private fun emptyJSONResponseBody(): ResponseBody =
			ResponseBody.create(MediaType.parse(Headers.MIME_JSON), emptyBody)

}