package org.amoustakos.boilerplate.net

import org.amoustakos.boilerplate.injection.annotations.network.DefaultRetrofitOptions
import org.amoustakos.utils.network.retrofit.RetrofitEngineOptions

object RetrofitConfig {

	@DefaultRetrofitOptions
	@JvmStatic
	fun defaultOptions() =
			RetrofitEngineOptions("http://dummy.io")

}