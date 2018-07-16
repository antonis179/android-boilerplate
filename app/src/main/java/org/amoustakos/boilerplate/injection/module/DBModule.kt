package org.amoustakos.boilerplate.injection.module


import dagger.Module
import dagger.Provides
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealmConfig
import org.amoustakos.boilerplate.io.RealmConfig

@Module
object DBModule {

	@Provides
	@DefaultRealmConfig
	fun provideDefaultRealmConfig() = RealmConfig.defaultConfig()

}
