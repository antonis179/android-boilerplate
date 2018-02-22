package org.amoustakos.boilerplate.injection.component;

import android.app.Application;
import android.content.Context;

import org.amoustakos.boilerplate.Environment;
import org.amoustakos.boilerplate.examples.io.local.dao.ExampleDao;
import org.amoustakos.boilerplate.examples.io.remote.ExampleNetCall;
import org.amoustakos.boilerplate.injection.annotations.context.ApplicationContext;
import org.amoustakos.boilerplate.injection.annotations.network.DefaultOkHttpClient;
import org.amoustakos.boilerplate.injection.annotations.network.DefaultOkHttpOptions;
import org.amoustakos.boilerplate.injection.annotations.network.DefaultRetrofitEngine;
import org.amoustakos.boilerplate.injection.annotations.network.DefaultRetrofitOptions;
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealm;
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealmConfig;
import org.amoustakos.boilerplate.injection.module.ApplicationModule;
import org.amoustakos.boilerplate.injection.module.DBModule;
import org.amoustakos.boilerplate.injection.module.NetworkModule;
import org.amoustakos.utils.network.retrofit.OkhttpOptions;
import org.amoustakos.utils.network.retrofit.RetrofitEngineOptions;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {
		ApplicationModule.class,
		DBModule.class,
		NetworkModule.class
})
public interface ApplicationComponent {


	// =========================================================================================
	// Base
	// =========================================================================================

	@ApplicationContext
    Context context();
    Application application();
    Environment environment();


	// =========================================================================================
	// DB
	// =========================================================================================

	@DefaultRealm Realm realm();

	@DefaultRealmConfig
	RealmConfiguration defaultRealmConfig();

	// =========================================================================================
	// DAOs
	// =========================================================================================

	ExampleDao exampleDao();


	// =========================================================================================
	// Network
	// =========================================================================================
	@DefaultRetrofitOptions RetrofitEngineOptions defaultRetrofitOptions();
	@DefaultOkHttpOptions OkhttpOptions defaultOkHttpOptions();

	@DefaultOkHttpClient OkHttpClient defaultOkHttpClient();
	@DefaultRetrofitEngine Retrofit defaultRetrofitEngine();

	ExampleNetCall.ApiService exampleApiService();


	// =========================================================================================
	// Injections
	// =========================================================================================



}
