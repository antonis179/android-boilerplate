package org.amoustakos.utils.network.retrofit.plugins.policies.pre

import io.reactivex.ObservableTransformer
import org.amoustakos.utils.network.retrofit.plugins.interfaces.policies.pre.ErrorLogPolicy
import retrofit2.Response
import timber.log.Timber

class DefaultErrorLogPolicy: ErrorLogPolicy {

	override fun <Model: Any, Resp: Response<Model>> apply(): ObservableTransformer<Resp, Resp> {
		return ObservableTransformer {
			it.doOnError(Timber::e)
		}
	}

}