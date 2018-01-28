package org.amoustakos.boilerplate.io.remote;

import org.amoustakos.utils.network.HttpStatusCode;
import org.amoustakos.utils.network.retrofit.AuthInterface;
import org.amoustakos.utils.network.retrofit.NetEvent;

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
	 * Applies the default retries to an observable: <br />
	 */
	public <T> ObservableTransformer<T, T> applyRetries(int times) {
		return tObservable ->
				tObservable.retry(times);
	}

	/**
	 * Applies the default error logging to an observable (warning): <br />
	 */
	public <T> ObservableTransformer<T, T> applyErrorLogging() {
		return tObservable ->
				tObservable.doOnError(Timber::w);
	}


	/**
	 * Adds an authorization call to an observable.
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



	public <T> ObservableTransformer<NetEvent<T>, NetEvent<T>> applyMapErrorResponse() {
		return tObservable ->
				tObservable.onErrorReturn(t -> new NetEvent<T>(true));
	}

	/**
	 * Applies the default error return to an observable: <br />
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
	 * Applies the following transformations to an observable: <br />
	 * <br />
	 * 1. Retry policy <br />
	 * 2. Default error logging (@ warning level) <br />
	 * 3. Default error return
	 */
	public <T> ObservableTransformer<Response<T>, Response<T>> applyTransformations(int retries) {
		return tObservable ->
				tObservable.compose(applyRetries(retries))
						.compose(applyErrorLogging())
						.compose(applyErrorReturn());
	}


	public <T> Response<T> makeErrorJsonResponse() {
		return Response.error(500, emptyJSONResponseBody());
	}


	public ResponseBody emptyJSONResponseBody() {
		return ResponseBody.create(MediaType.parse(MIME_JSON), "{}");
	}



}
