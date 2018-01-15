package org.amoustakos.utils.network.retrofit

import com.google.gson.GsonBuilder
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Arrays.asList


/**
 * Use this class to provide arguments for [RetrofitBuilder.getRetrofitEngine].
 *
 * By default a [GsonConverterFactory] and an [RxJava2CallAdapterFactory] are used.
 */

data class RetrofitEngineOptions(

        val baseUrl: String,


        val converterFactories: List<Converter.Factory> =
        asList(GsonConverterFactory.create(GsonBuilder().create())),
        val adapterFactories: List<CallAdapter.Factory> =
        asList(RxJava2CallAdapterFactory.create())
)