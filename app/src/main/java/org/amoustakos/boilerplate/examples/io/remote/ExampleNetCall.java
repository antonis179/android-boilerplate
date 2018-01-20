package org.amoustakos.boilerplate.examples.io.remote;

import org.amoustakos.boilerplate.io.remote.BaseCall;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static org.amoustakos.boilerplate.io.remote.Headers.HEADER_APPLICATION_JSON;
import static org.amoustakos.boilerplate.io.remote.Headers.HEADER_CONTENT_TYPE;

/**
 * Created by antonis on 17/01/2018.
 */

public class ExampleNetCall extends BaseCall {

	private final ApiService service;


	// =========================================================================================
	// Constructors
	// =========================================================================================

	@Inject
	public ExampleNetCall(ApiService service) {
		this.service = service;
	}


	// =========================================================================================
	// Implementation
	// =========================================================================================






	// =========================================================================================
	// Inner classes
	// =========================================================================================

	public interface ApiService {
		@Headers(HEADER_CONTENT_TYPE + HEADER_APPLICATION_JSON)
		@POST("/endpoint")
		Observable<Response<JSONObject>> makeCall();
	}



}
