package org.amoustakos.utils.network.retrofit.plugins.interfaces.policies.post

import io.reactivex.ObservableTransformer
import org.amoustakos.utils.network.http.models.NetResult
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.PostPolicy

interface MapErrorReturnPolicy : PostPolicy {

	override fun <Model : Any> apply(): ObservableTransformer<NetResult<Model>, NetResult<Model>> {
		return ObservableTransformer { obs ->
			obs.onErrorReturn { NetResult.Error(it) }
		}
	}

}