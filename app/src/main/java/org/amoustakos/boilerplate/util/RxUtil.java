package org.amoustakos.boilerplate.util;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    /*
     ************ Schedulers **************
     */

    /**
     * Applies default IO schedulers on an Observable. <br />
     * Subscribe on: io thread <br />
     * Observe on: Main thread
     */
    public static <T> ObservableTransformer<T, T> applyDefaultSchedulers() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Applies default foreground schedulers on an Observable. <br />
     * Subscribe on: Main thread <br />
     * Observe on: Main thread
     */
    public static <T> ObservableTransformer<T, T> applyForegroundSchedulers() {
        return tObservable -> tObservable.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Applies default background IO schedulers on an Observable. <br />
     * Subscribe on: io thread <br />
     * Observe on: io thread
     */
    public static <T> ObservableTransformer<T, T> applyBackgroundIOSchedulers() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    /**
     * Applies default computation schedulers on an Observable. <br />
     * Subscribe on: computation thread <br />
     * Observe on: computation thread
     */
    public static <T> ObservableTransformer<T, T> applyComputationSchedulers() {
        return tObservable -> tObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Applies trampoline schedulers on an Observable. <br />
     * <p>
     * Subscribe on: trampoline thread <br />
     * Observe on: trampoline thread
     */
    public static <T> ObservableTransformer<T, T> applyTrampolineSchedulers() {
        return tObservable -> tObservable.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline());
    }


    /*
     ************ Policies **************
     */

    /**
     * Applies a retry policy to your observable (will retry execution upon failure).
     */
    public static <T> ObservableTransformer<T, T> applyRetryPolicy(final int retry) {
        return tObservable -> tObservable.retry(retry);
    }


	/*
	 *********** Subscriptions ************
	 * TODO: Make subscription handling utility methods to be used in activity cancellations etc.
	 */





	/*
	 *********** Testing *****************
	 */

    /**
     * Method to check a {@link Predicate} without throwing an error. <br />
     * Useful for testing.
     */
    public static <T> Predicate<T> check(Consumer<T> consumer) {
        return t -> {
            consumer.accept(t);
            return true;
        };
    }
}
