package org.amoustakos.boilerplate.di.module


import dagger.Module
import dagger.Provides
import org.amoustakos.boilerplate.di.annotations.realm.DefaultRealmConfig
import org.amoustakos.boilerplate.io.realm.RealmConfig

@Module
object DBModule {

	@Provides
	@DefaultRealmConfig
	fun provideDefaultRealmConfig() = RealmConfig.defaultConfig()

}
