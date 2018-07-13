package org.amoustakos.boilerplate

import android.app.Application
import android.content.Context

import org.amoustakos.boilerplate.injection.component.ApplicationComponent
import org.amoustakos.boilerplate.injection.component.DaggerApplicationComponent
import org.amoustakos.boilerplate.injection.module.ApplicationModule
import timber.log.Timber


class BoilerplateApplication : Application() {

	lateinit var component: ApplicationComponent

	override fun onCreate() {
		val start = System.currentTimeMillis()
		super.onCreate()

		makeComponent()

		component.environment().init()

		val end = System.currentTimeMillis() - start
		Timber.d("Application initialized in $end ms.")
	}

	override fun onLowMemory() {
		super.onLowMemory()
		//TODO: EVENT
	}

	private fun makeComponent() {
		component = DaggerApplicationComponent.builder()
				.applicationModule(ApplicationModule(this))
				.build()
	}



	companion object {
		@JvmStatic operator fun get(context: Context): BoilerplateApplication {
			return context.applicationContext as BoilerplateApplication
		}
	}

}
