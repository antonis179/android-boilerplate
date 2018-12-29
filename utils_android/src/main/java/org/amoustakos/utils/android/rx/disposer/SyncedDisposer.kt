package org.amoustakos.utils.android.rx.disposer

import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import org.amoustakos.utils.android.kotlin.pollAndClear
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock


internal class SyncedDisposer : Disposer {

    private val lock = ReentrantLock()

    private val disposables = mutableListOf<Disposable>()

    override fun add(disposable: Disposable): Unit = lock.withLock {
        disposables.add(disposable)
    }

    override fun plusAssign(disposable: Disposable) = add(disposable)

    override fun <T> bind(observable: Observable<T>): Observable<T> {
        return observable.doOnSubscribe { disposable ->
            add(disposable)
        }
    }

    override fun <T> bind(flowable: Flowable<T>): Flowable<T> {
        return flowable.doOnSubscribe { subscription ->
            this += Disposables.fromSubscription(subscription)
        }
    }

    override fun <T> bind(single: Single<T>): Single<T> {
        return single.doOnSubscribe(this::add)
    }

    override fun <T> bind(maybe: Maybe<T>): Maybe<T> {
        return maybe.doOnSubscribe(this::add)
    }

    override fun bind(completable: Completable): Completable {
        return completable.doOnSubscribe(this::add)
    }

    override fun dispose() {
        val disposables = lock.withLock(disposables::pollAndClear)
        for (disposable in disposables) {
            disposable.dispose()
        }
    }
}