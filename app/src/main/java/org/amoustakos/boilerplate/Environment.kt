@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package org.amoustakos.boilerplate

import android.content.Context
import com.vicpin.krealmextensions.RealmConfigStore
import io.realm.Realm
import io.realm.RealmConfiguration
import org.amoustakos.boilerplate.examples.io.local.models.ExampleModel
import org.amoustakos.boilerplate.injection.annotations.context.ApplicationContext
import org.amoustakos.boilerplate.io.local.migrations.base.Migration
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Environment @Inject
constructor(@ApplicationContext context: Context) {

    private val context: WeakReference<Context> = WeakReference(context)


    companion object {
        private const val REALM_SCHEMA_VERSION = 0
    }


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
        val realmConfig = RealmConfiguration.Builder()
                .schemaVersion(REALM_SCHEMA_VERSION.toLong())
                .migration(Migration())
                .build()
        Realm.setDefaultConfiguration(realmConfig)
        Realm.getDefaultInstance().isAutoRefresh = true

        RealmConfigStore.init(ExampleModel::class.java, realmConfig)
    }

}
