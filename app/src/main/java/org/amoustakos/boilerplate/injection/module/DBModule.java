package org.amoustakos.boilerplate.injection.module;


import org.amoustakos.boilerplate.examples.io.local.dao.ExampleD;
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealm;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class DBModule {



	/*
	 * Realm
	 */

	/**
	 * !IMPORTANT! <br />
	 * Do not make this provider a singleton or you will run into unexpected <br />
	 * behaviour when closing a realm instance
	 */
	@Provides
	@DefaultRealm
	Realm provideRealm() {
		return Realm.getDefaultInstance();
	}





	/*
	 * DAOs
	 */

	@Provides @Singleton
	ExampleD provideExampleDao(@DefaultRealm Realm realm) {
		return new ExampleD(realm);
	}





}
