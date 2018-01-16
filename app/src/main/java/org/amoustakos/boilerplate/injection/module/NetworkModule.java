package org.amoustakos.boilerplate.injection.module;

import org.amoustakos.boilerplate.injection.annotations.network.DefaultOkHttpOptions;
import org.amoustakos.boilerplate.injection.annotations.network.DefaultRetrofitOptions;
import org.amoustakos.utils.network.retrofit.OkhttpOptions;
import org.amoustakos.utils.network.retrofit.RetrofitEngineOptions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
		return new OkhttpOptions(10 * 1024 * 1024L);
	}

	// =========================================================================================
	// Retrofit
	// =========================================================================================


	// =========================================================================================
	// APIs
	// =========================================================================================


}
