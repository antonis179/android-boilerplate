package org.amoustakos.utils.network.retrofit

import retrofit2.CallAdapter
import retrofit2.Converter


/**
 * Class used to provide arguments for [RetrofitFactory.getRetrofitEngine].
 */
data class RetrofitEngineOptions (
        val baseUrl: String,
        val converterFactories: List<Converter.Factory>,
        val adapterFactories: List<CallAdapter.Factory>
)