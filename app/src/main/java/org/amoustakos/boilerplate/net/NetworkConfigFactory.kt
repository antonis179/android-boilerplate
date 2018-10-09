package org.amoustakos.boilerplate.net

import org.amoustakos.boilerplate.injection.annotations.network.DefaultOkHttpOptions
import org.amoustakos.boilerplate.injection.annotations.network.DefaultRetrofitOptions
import org.amoustakos.utils.network.retrofit.OkhttpOptions
import org.amoustakos.utils.network.retrofit.RetrofitEngineOptions
import java.util.concurrent.TimeUnit

object NetworkConfigFactory {

	@DefaultRetrofitOptions
	@JvmStatic
	fun defaultRetrofitOptions() =
			RetrofitEngineOptions("http://dummy.io")



	@DefaultOkHttpOptions
	@JvmStatic
	fun defaultOkhttpOptions(debug: Boolean) = OkhttpOptions(
			debug,
			-1L,
			null,
			null,
			15,
			15,
			15,
			TimeUnit.SECONDS,
			null
	)

}