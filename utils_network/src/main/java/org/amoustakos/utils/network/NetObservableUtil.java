package org.amoustakos.utils.network;

import io.reactivex.ObservableTransformer;
import timber.log.Timber;

/**
 * Created by antonis on 18/01/2018.
 */

public final class NetObservableUtil {


	/**
	 * Applies the default retries to an observable: <br />
	 */
	public static <T> ObservableTransformer<T, T> applyRetries(int times) {
		return tObservable ->
				tObservable.retry(times);
	}

	/**
	 * Applies the default error logging to an observable (warning): <br />
	 */
	public static <T> ObservableTransformer<T, T> applyErrorLogging() {
		return tObservable ->
				tObservable.doOnError(Timber::w);
	}

//	/**
//	 * Applies the default error return to an observable: <br />
//	 */
//	public static <T> ObservableTransformer<Response<T>, Response<T>> applyErrorReturn() {
//		return tObservable ->
//				tObservable.onErrorReturn(t -> {
//					if (t instanceof HttpException)
//						return (Response<T>) ((HttpException) t).response();
//					return makeErrorJsonResponse();
//				});
//	}

//	/**
//	 * Applies the following transformations to an observable: <br />
//	 * <br />
//	 * 1. Retry policy <br />
//	 * 2. Default error logging (@ warning level) <br />
//	 * 3. Default error return
//	 */
//	public static <T> ObservableTransformer<Response<T>, Response<T>> applyTransformations(int retries) {
//		return tObservable ->
//				tObservable.compose(applyRetries(retries))
//						.compose(applyErrorLogging())
//						.compose(applyErrorReturn());
//	}

//	public static <T> ObservableTransformer<Event<T>, Event<T>> applyMapErrorResponse() {
//		return tObservable ->
//				tObservable.onErrorReturn(t -> new Event<T>(true));
//	}
//
//	public static <T> Response<T> makeErrorJsonResponse() {
//		return Response.error(500, emptyJSONResponseBody());
//	}
//
//
//	public static ResponseBody emptyJSONResponseBody() {
//		return ResponseBody.create(MediaType.parse(HttpConstants.Headers.HEADER_JSON), "{}");
//	}
//
//	/**
//	 * Adds an authorization call to an observable. Scenarios: <br />
//	 * <br />
//	 * 1. Auth has expired: Auth call -> Observable <br />
//	 * 2. Auth failed: Observable (401) -> Auth call -> Observable
//	 */
//	protected <T> Observable<T> auth(Observable<T> obs, AuthenticationApiManager authManager) {
//		if (authManager == null)
//			throw new NullPointerException("You have to set the auth manager in the constructor to use this method");
//
//		if (AuthenticationApiManager.isAuthenticated()) {
//			return obs.onErrorResumeNext(throwable -> {
//				if (HttpStatusCodes.isUnauthorized(throwable))
//					return authManager.authenticateNoCompose()
//							.flatMap(aBoolean -> obs);
//				else
//					return Observable.error(throwable);
//			});
//		} else {
//			return authManager.authenticateNoCompose()
//					.flatMap(aBoolean -> obs);
//		}
//	}

}
