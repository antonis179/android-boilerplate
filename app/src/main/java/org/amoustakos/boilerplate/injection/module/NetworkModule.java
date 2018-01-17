package org.amoustakos.boilerplate.injection.module;

import org.amoustakos.boilerplate.BuildConfig;
import org.amoustakos.boilerplate.examples.io.remote.ExampleNetCall;
import org.amoustakos.boilerplate.injection.annotations.network.DefaultOkHttpClient;
import org.amoustakos.boilerplate.injection.annotations.network.DefaultOkHttpOptions;
import org.amoustakos.boilerplate.injection.annotations.network.DefaultRetrofitEngine;
import org.amoustakos.boilerplate.injection.annotations.network.DefaultRetrofitOptions;
import org.amoustakos.utils.network.retrofit.OkhttpOptions;
import org.amoustakos.utils.network.retrofit.RetrofitBuilder;
import org.amoustakos.utils.network.retrofit.RetrofitEngineOptions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by antonis on 15/01/2018.
 */

@Module
public class NetworkModule {

	public NetworkModule() {
	}

	// =========================================================================================
	// Interceptors
	// =========================================================================================


	// =========================================================================================
	// Options
	// =========================================================================================

	@Provides
	@Singleton
	@DefaultRetrofitOptions
	RetrofitEngineOptions provideDefaultRetrofitOptions() {
		return new RetrofitEngineOptions("http://dummy.com"); //TODO
	}

	@Provides
	@Singleton
	@DefaultOkHttpOptions
	OkhttpOptions provideDefaultOkHttpOptions() {
		return new OkhttpOptions(BuildConfig.DEBUG);
	}

	// =========================================================================================
	// Retrofit
	// =========================================================================================

	@Provides
	@Singleton
	@DefaultOkHttpClient
	OkHttpClient provideDefaultOkHttpClient(@DefaultOkHttpOptions OkhttpOptions opts) {
		return RetrofitBuilder.getHttpClient(opts);
	}

	@Provides
	@Singleton
	@DefaultRetrofitEngine
	Retrofit provideDefaultRetrofitEngine(
			@DefaultOkHttpClient OkHttpClient client,
			@DefaultRetrofitOptions RetrofitEngineOptions opts) {
		return RetrofitBuilder.getRetrofitEngine(client, opts);
	}


	// =========================================================================================
	// APIs
	// =========================================================================================

	@Provides
	@Singleton
	ExampleNetCall.ApiService provideExampleApiService(@DefaultRetrofitEngine Retrofit engine) {
		return RetrofitBuilder.newService(engine, ExampleNetCall.ApiService.class);
	}


}
