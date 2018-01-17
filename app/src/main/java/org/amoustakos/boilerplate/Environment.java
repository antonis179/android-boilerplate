package org.amoustakos.boilerplate;

import android.content.Context;

import org.amoustakos.boilerplate.injection.annotations.context.ApplicationContext;
import org.amoustakos.boilerplate.io.local.migrations.base.Migration;

import java.lang.ref.WeakReference;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@Singleton
public class Environment {
    private static final int REALM_SCHEMA_VERSION = 0;
    private final WeakReference<Context> context;

    @Inject
    public Environment(@ApplicationContext Context context) {
        this.context = new WeakReference<>(context);
    }



    /*
     * Init methods
     */
    void init(){
        initPrefs();
        initLog();
        initRealm();
        initFonts();
    }

    private void initPrefs(){}

    private void initLog(){
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }

    private void initFonts(){
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("ubuntu/Ubuntu-condensed.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void initRealm(){
        Realm.init(context.get());
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder()
                .schemaVersion(REALM_SCHEMA_VERSION)
                .migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(realmConfig);
	    Realm.getDefaultInstance().setAutoRefresh(true);
    }



}
