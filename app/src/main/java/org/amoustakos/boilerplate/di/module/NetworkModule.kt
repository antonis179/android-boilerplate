package org.amoustakos.boilerplate.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.amoustakos.boilerplate.BuildConfig
import org.amoustakos.boilerplate.di.annotations.network.*
import org.amoustakos.boilerplate.net.NetworkConfigFactory
import org.amoustakos.utils.network.retrofit.OkhttpOptions
import org.amoustakos.utils.network.retrofit.RetrofitEngineOptions
import org.amoustakos.utils.network.retrofit.RetrofitFactory
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.DecoratorPolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.PostPolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.PrePolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.policies.appliers.DefaultDecoratorPolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.policies.appliers.DefaultPostPolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.policies.appliers.DefaultPrePolicyApplier
import retrofit2.Retrofit
import javax.inject.Singleton

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
			@DefaultRetrofitOptions opts: RetrofitEngineOptions
	): Retrofit {
		return RetrofitFactory.getRetrofitEngine(client, opts)
	}

	// =========================================================================================
	// Policy Appliers
	// =========================================================================================

	@Provides
	@Singleton
	@IDefaultPrePolicyApplier
	internal fun provideDefaultPrePolicyApplier(): PrePolicyApplier = DefaultPrePolicyApplier()

	@Provides
	@Singleton
	@IDefaultPostPolicyApplier
	internal fun provideDefaultPostPolicyApplier(): PostPolicyApplier = DefaultPostPolicyApplier()

	@Provides
	@Singleton
	@IDefaultDecoratorPolicyApplier
	internal fun provideDefaultDecoratorPolicyApplier(): DecoratorPolicyApplier =
			DefaultDecoratorPolicyApplier()

}
