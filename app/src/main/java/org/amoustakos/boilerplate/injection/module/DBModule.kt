package org.amoustakos.boilerplate.injection.module


import dagger.Module
import dagger.Provides
import io.realm.Realm
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealm
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealmConfig
import org.amoustakos.boilerplate.io.RealmConfig

@Module
object DBModule {


	// =========================================================================================
	// Realm
	// =========================================================================================

	/**
	 * !IMPORTANT! <br></br>
	 * Do not make this provider a singleton. Let Realm handle caching.
	 */
	@Provides
	@DefaultRealm
	internal fun provideRealm() = Realm.getDefaultInstance()


	// =========================================================================================
	// Configs
	// =========================================================================================

	@Provides
	@DefaultRealmConfig
	internal fun provideDefaultRealmConfig() = RealmConfig.defaultConfig()

}
