@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package org.amoustakos.boilerplate

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import org.amoustakos.boilerplate.injection.annotations.context.ApplicationContext
import org.amoustakos.boilerplate.injection.annotations.realm.DefaultRealmConfig
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Environment @Inject
constructor(
        @ApplicationContext context: Context,
        @DefaultRealmConfig private val realmConfig: RealmConfiguration){

    private val context: WeakReference<Context> = WeakReference(context)


    /*
     * Init methods
     */
    fun init() {
        initPrefs()
        initLog()
        initRealm()
        initFonts()
    }

    private fun initPrefs() {}

    private fun initLog() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun initFonts() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("ubuntu/Ubuntu-condensed.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }

    private fun initRealm() {
        Realm.init(context.get())
        Realm.setDefaultConfiguration(realmConfig)
    }

}
