package org.amoustakos.utils.analytics;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public abstract class Event {

	// =========================================================================================
	// Constants
	// =========================================================================================

	protected static final String SPACE = " ";


	// =========================================================================================
	// Variables
	// =========================================================================================

	protected final WeakReference<Context> context;

	protected Map<String, String> attributes;
	protected String event;
	@EventAction
	protected String action;


	// =========================================================================================
	// Constructors
	// =========================================================================================

	public Event(@NonNull Context context) {
		this.context = new WeakReference<>(context);
	}

	public Event(
			@NonNull Context context,
			Map<String, String> attributes,
			@NonNull String event,
			@NonNull @EventAction String action
	) {
		this(context);
		this.attributes = attributes;
		this.event = event;
		this.action = action;
	}


	// =========================================================================================
	// Loggers
	// =========================================================================================

	public abstract List<EventLogger> getLoggers();

	// =========================================================================================
	// Event handling
	// =========================================================================================

	public Observable<Boolean> logAllAsync() {
		return Observable.fromCallable(() -> {
				logAll();
				return true;
			})
			.observeOn(Schedulers.io())
			.subscribeOn(Schedulers.io())
			.doOnError(Timber::e)
			.onErrorReturn(t -> false);
	}

	public void logAll() {
		logAll(getEvent(), getAction(), getAttributes());
	}

	public void logAll(
			@NonNull String eventName,
			@NonNull @EventAction String action,
			Map<String, String> attributes) throws NullPointerException {

		if (!shouldTag())           return;
		if (attributes == null)     attributes = new HashMap<>();

		//Log
		for (EventLogger logger : getLoggers())
			logger.logEvent(eventName, action, attributes);


		//Print info
		String message = "Event: " +
				eventName + SPACE +
				action + SPACE +
				(attributes.size() > 0 ? attributes.toString() : "");

		Timber.d(message);
	}


	// =========================================================================================
	// Helpers
	// =========================================================================================



	// =========================================================================================
	// Options
	// =========================================================================================

	//TODO
	public boolean shouldTag() {
		return true;
	}

	// =========================================================================================
	// Getters - Setters
	// =========================================================================================

	public synchronized void setEventData(
			Map<String, String> attributes,
			@NonNull String event,
			@NonNull @EventAction String action
	) {
		this.attributes = attributes;
		this.event = event;
		this.action = action;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public synchronized void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getEvent() {
		return event;
	}

	public synchronized void setEvent(String event) {
		this.event = event;
	}

	public String getAction() {
		return action;
	}

	public synchronized void setAction(String action) {
		this.action = action;
	}

}
