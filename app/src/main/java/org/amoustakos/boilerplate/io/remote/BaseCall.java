package org.amoustakos.boilerplate.io.remote;

import org.amoustakos.models.network.NetEvent;
import org.amoustakos.utils.network.HttpStatusCode;
import org.amoustakos.utils.network.retrofit.AuthInterface;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import timber.log.Timber;

import static org.amoustakos.utils.network.Headers.MIME_JSON;


public abstract class BaseCall {


	/**
	 * Applies the default retries to an observable
	 */
	public <T> ObservableTransformer<T, T> applyRetries(int times) {
		return tObservable ->
				tObservable.retry(times);
	}

	/**
	 * Applies the default error logging to an observable
	 */
	public <T> ObservableTransformer<T, T> applyErrorLogging() {
		return tObservable ->
				tObservable.doOnError(Timber::w);
	}


	/**
	 * Adds an authorization call to an observable
	 */
	protected <T> Observable<T> auth(Observable<T> obs, AuthInterface authManager) {
		if (authManager.isAuthorized()) {
			return obs.onErrorResumeNext(throwable -> {
				if (HttpStatusCode.isUnauthorized(throwable))
					return authManager.authorizeAsync()
							.flatMap(aBoolean -> obs);
				else
					return Observable.error(throwable);
			});
		} else {
			return authManager.authorizeAsync()
					.flatMap(aBoolean -> obs);
		}
	}


	/**
	 * Applies a return value in case a mapping operation fails
	 */
	public <T> ObservableTransformer<NetEvent<T>, NetEvent<T>> applyMapErrorResponse() {
		return tObservable ->
				tObservable.onErrorReturn(t ->
						new NetEvent<T>(-1, true, null));
	}

	/**
	 * Applies the default error return to an observable
	 */
	public <T> ObservableTransformer<Response<T>, Response<T>> applyErrorReturn() {
		return tObservable ->
				tObservable.onErrorReturn(t -> {
					if (t instanceof HttpException)
						return (Response<T>) ((HttpException) t).response();
					return makeErrorJsonResponse();
				});
	}

	/**
	 * Applies the following transformations to an observable:
	 *
	 * 1. Retry policy
	 * 2. Default error logging (@ warning level)
	 * 3. Default error return
	 */
	public <T> ObservableTransformer<Response<T>, Response<T>> applyTransformations(int retries) {
		return tObservable ->
				tObservable.compose(applyRetries(retries))
						.compose(applyErrorLogging())
						.compose(applyErrorReturn());
	}


	/**
	 * Default error JSON response
	 */
	public <T> Response<T> makeErrorJsonResponse() {
		return Response.error(500, emptyJSONResponseBody());
	}


	/**
	 * Default error JSON body
	 */
	public ResponseBody emptyJSONResponseBody() {
		return ResponseBody.create(MediaType.parse(MIME_JSON), "{}");
	}



}
