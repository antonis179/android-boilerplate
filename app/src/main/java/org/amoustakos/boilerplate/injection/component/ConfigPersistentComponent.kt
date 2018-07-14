package org.amoustakos.boilerplate.injection.component


import dagger.Component
import org.amoustakos.boilerplate.injection.annotations.scopes.ConfigPersistent
import org.amoustakos.boilerplate.injection.module.injectors.ActivityModule
import org.amoustakos.boilerplate.injection.module.injectors.DialogModule
import org.amoustakos.boilerplate.injection.module.injectors.FragmentModule
import org.amoustakos.boilerplate.ui.activities.BaseActivity

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check [BaseActivity] to see how this components
 * survives configuration changes.
 *
 * Use the [ConfigPersistent] scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = [
	ApplicationComponent::class
])
interface ConfigPersistentComponent {

	fun activityComponent(activityModule: ActivityModule): ActivityComponent
	fun fragmentComponent(fragmentModule: FragmentModule): FragmentComponent
	fun dialogComponent(module: DialogModule): DialogComponent

}