package org.amoustakos.boilerplate.injection.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.amoustakos.boilerplate.BuildConfig
import org.amoustakos.boilerplate.examples.io.remote.ExampleNetCall
import org.amoustakos.boilerplate.injection.annotations.network.DefaultOkHttpClient
import org.amoustakos.boilerplate.injection.annotations.network.DefaultOkHttpOptions
import org.amoustakos.boilerplate.injection.annotations.network.DefaultRetrofitEngine
import org.amoustakos.boilerplate.injection.annotations.network.DefaultRetrofitOptions
import org.amoustakos.boilerplate.net.NetworkConfigFactory
import org.amoustakos.utils.network.retrofit.OkhttpOptions
import org.amoustakos.utils.network.retrofit.RetrofitFactory
import org.amoustakos.utils.network.retrofit.RetrofitEngineOptions
import retrofit2.Retrofit

/**
 * Created by antonis on 15/01/2018.
 */

@Module
object NetworkModule {


	@Provides
	@DefaultRetrofitOptions
	internal fun provideDefaultRetrofitOptions() =
			NetworkConfigFactory.defaultRetrofitOptions()

	@Provides
	@DefaultOkHttpOptions
	internal fun provideDefaultOkHttpOptions() =
			NetworkConfigFactory.defaultOkhttpOptions(BuildConfig.DEBUG)

	@Provides
	@DefaultOkHttpClient
	internal fun provideDefaultOkHttpClient(@DefaultOkHttpOptions opts: OkhttpOptions): OkHttpClient {
		return RetrofitFactory.getHttpClient(opts)
	}

	@Provides
	@DefaultRetrofitEngine
	internal fun provideDefaultRetrofitEngine(
			@DefaultOkHttpClient client: OkHttpClient,
			@DefaultRetrofitOptions opts: RetrofitEngineOptions): Retrofit {
		return RetrofitFactory.getRetrofitEngine(client, opts)
	}



	// =========================================================================================
	// APIs
	// =========================================================================================

	@Provides
	internal fun provideExampleApiService(@DefaultRetrofitEngine engine: Retrofit) =
			RetrofitFactory.newService(engine, ExampleNetCall.ApiService::class.java)


}
