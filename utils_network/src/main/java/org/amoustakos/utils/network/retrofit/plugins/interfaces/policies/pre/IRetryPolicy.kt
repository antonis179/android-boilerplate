package org.amoustakos.utils.network.retrofit.plugins.interfaces.policies.pre

import io.reactivex.ObservableTransformer
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.IPrePolicy
import retrofit2.Response

interface IRetryPolicy: IPrePolicy {

	fun retries(): Long

	override fun <Model: Any, Resp: Response<Model>> apply(): ObservableTransformer<Resp, Resp> {
		return ObservableTransformer {
			it.retry(retries())
		}
	}

}