package org.amoustakos.utils.analytics.loggers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.localytics.android.Localytics;

import org.amoustakos.utils.analytics.EventAction;
import org.amoustakos.utils.analytics.EventLogger;

import java.lang.ref.WeakReference;
import java.util.Map;


public class LocalyticsEventLogger implements EventLogger<Void> {

	protected final WeakReference<Context> context;


	// =========================================================================================
	// Constructors
	// =========================================================================================

	public LocalyticsEventLogger(@NonNull Context context) {
		this.context = new WeakReference<>(context);
	}

	// =========================================================================================
	// Logging
	// =========================================================================================

	@Override
	public void logEvent(
			@NonNull String eventName,
			@Nullable String action,
			@NonNull Map<String, String> parameters) {

		Localytics.tagEvent(makeEventName(eventName, action), parameters);
	}


	@Override
	public Void transformParameters(@NonNull Map<String, String> attributes) {
		return null;
	}

	@Override
	public String makeEventName(@NonNull String eventName, @Nullable String action) {
		if (action == null) return eventName;

		StringBuilder event = new StringBuilder(eventName);
		if (!action.equals(EventAction.NONE)) {
			event.append(SPACE);
			event.append(action);
		}
		return event.toString();
	}


}