package org.amoustakos.boilerplate.injection.module;


import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealm;
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealmConfig;
import org.amoustakos.boilerplate.io.RealmConfig;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class DBModule {


	// =========================================================================================
	// Realm
	// =========================================================================================

	/**
	 * !IMPORTANT! <br />
	 * Do not make this provider a singleton. Let Realm handle caching.
	 */
	@Provides
	@DefaultRealm
	Realm provideRealm() {
		return Realm.getDefaultInstance();
	}


	// =========================================================================================
	// Configs
	// =========================================================================================

	@Provides
	@DefaultRealmConfig
	RealmConfiguration provideDefaultRealmConfig() {
		return RealmConfig.defaultConfig();
	}

}
