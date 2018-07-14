package org.amoustakos.boilerplate.injection.component

import android.app.Application
import android.content.Context
import dagger.Component
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import org.amoustakos.boilerplate.Environment
import org.amoustakos.boilerplate.examples.io.remote.ExampleNetCall
import org.amoustakos.boilerplate.injection.annotations.context.ApplicationContext
import org.amoustakos.boilerplate.injection.annotations.network.DefaultOkHttpClient
import org.amoustakos.boilerplate.injection.annotations.network.DefaultOkHttpOptions
import org.amoustakos.boilerplate.injection.annotations.network.DefaultRetrofitEngine
import org.amoustakos.boilerplate.injection.annotations.network.DefaultRetrofitOptions
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealm
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealmConfig
import org.amoustakos.boilerplate.injection.module.ApplicationModule
import org.amoustakos.boilerplate.injection.module.DBModule
import org.amoustakos.boilerplate.injection.module.NetworkModule
import org.amoustakos.utils.network.retrofit.OkhttpOptions
import org.amoustakos.utils.network.retrofit.RetrofitEngineOptions
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [
	ApplicationModule::class,
	DBModule::class,
	NetworkModule::class
])
interface ApplicationComponent {


	// =========================================================================================
	// Base
	// =========================================================================================

	@ApplicationContext
	fun context(): Context

	fun application(): Application
	fun environment(): Environment


	// =========================================================================================
	// DB
	// =========================================================================================

	@DefaultRealm
	fun realm(): Realm

	@DefaultRealmConfig
	fun defaultRealmConfig(): RealmConfiguration

	// =========================================================================================
	// Network
	// =========================================================================================

	@DefaultRetrofitOptions
	fun defaultRetrofitOptions(): RetrofitEngineOptions

	@DefaultOkHttpOptions
	fun defaultOkHttpOptions(): OkhttpOptions

	@DefaultOkHttpClient
	fun defaultOkHttpClient(): OkHttpClient

	@DefaultRetrofitEngine
	fun defaultRetrofitEngine(): Retrofit

	fun exampleApiService(): ExampleNetCall.ApiService

}
