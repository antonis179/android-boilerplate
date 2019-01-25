package org.amoustakos.boilerplate.net

import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import org.amoustakos.boilerplate.di.annotations.network.DefaultOkHttpOptions
import org.amoustakos.boilerplate.di.annotations.network.DefaultRetrofitOptions
import org.amoustakos.utils.network.retrofit.OkHttpConnection
import org.amoustakos.utils.network.retrofit.OkHttpLogging
import org.amoustakos.utils.network.retrofit.OkhttpOptions
import org.amoustakos.utils.network.retrofit.RetrofitEngineOptions
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object NetworkConfigFactory {

	@DefaultRetrofitOptions
	@JvmStatic
	fun defaultRetrofitOptions() = RetrofitEngineOptions(
			baseUrl = "https://jsonplaceholder.typicode.com",
			converterFactories = Arrays.asList( GsonConverterFactory.create(Gson()) ),
			adapterFactories = Arrays.asList(
					RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
			)
	)



	@DefaultOkHttpOptions
	@JvmStatic
	fun defaultOkhttpOptions(logEnabled: Boolean) = OkhttpOptions(
			OkHttpLogging(logEnabled),
			connection = OkHttpConnection(
					connectTimeout = 15,
					readTimeout = 15,
					writeTimeout = 15,
					timeUnit = TimeUnit.SECONDS
			)
	)

}