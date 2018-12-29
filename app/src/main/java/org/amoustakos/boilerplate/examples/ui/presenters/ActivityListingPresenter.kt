package org.amoustakos.boilerplate.examples.ui.presenters

import android.arch.lifecycle.Lifecycle
import android.content.pm.PackageManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingActions
import org.amoustakos.boilerplate.examples.ui.contracts.ActivityListingView
import org.amoustakos.boilerplate.examples.view.models.ActivityListingModel
import org.amoustakos.boilerplate.ui.activities.BaseActivity
import org.amoustakos.boilerplate.ui.presenters.BasePresenter
import org.amoustakos.utils.android.PackageManagerUtils.definedActivities
import org.amoustakos.utils.android.rx.disposer.LifecycleDisposers
import org.amoustakos.utils.android.rx.disposer.disposeBy
import org.amoustakos.utils.android.rx.disposer.onDestroy
import timber.log.Timber
import java.util.*


class ActivityListingPresenter(
	view: ActivityListingView,
	private val basePackage: String,
	private val pm: PackageManager,
	lifecycle: Lifecycle
): BasePresenter<ActivityListingView>(view, lifecycle), ActivityListingActions {


	private val activities: List<Class<out BaseActivity>>
		get() = definedActivities(pm, basePackage)


	override fun init() {}

	override fun load() {
		Observable.just {}
			.disposeBy(lifecycle.onDestroy)
			.observeOn(Schedulers.computation())
			.map<List<ActivityListingModel>> {
				val models = ArrayList<ActivityListingModel>()
				val activities = excludeCurrent(activities)

				for (act in activities) {
					val model = ActivityListingModel(
                            act,
                            act.simpleName,
                            act.superclass?.name
                    )
					models.add(model)
				}
				models
			}
			.doOnError { Timber.i(it) }
			.onErrorReturn { ArrayList() }
			.observeOn(AndroidSchedulers.mainThread())
			.doOnNext { view().onItemsCollected(it) }
			.subscribe()
	}


	private fun excludeCurrent(classes: List<Class<out BaseActivity>>) =
			classes.filter{ item -> mView.get()?.javaClass != item }

}
