package org.amoustakos.utils.analytics.loggers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.appevents.AppEventsLogger;

import org.amoustakos.utils.analytics.EventAction;
import org.amoustakos.utils.analytics.EventLogger;

import java.util.Map;

public class FacebookEventLogger implements EventLogger<Bundle> {

	private final AppEventsLogger logger;


	// =========================================================================================
	// Constructors
	// =========================================================================================

	public FacebookEventLogger(@NonNull AppEventsLogger logger) {
		this.logger = logger;
	}

	// =========================================================================================
	// Logging
	// =========================================================================================

	@Override
	public void logEvent(
			@NonNull String eventName,
			@Nullable String action,
			@NonNull Map<String, String> parameters) {
		logger.logEvent(makeEventName(eventName, action), transformParameters(parameters));
	}


	@Override
	public Bundle transformParameters(@NonNull Map<String, String> attributes) {
		Bundle bundle = new Bundle();
		for (Map.Entry<String, String> attributesEntrySet : attributes.entrySet()) {
			bundle.putString(attributesEntrySet.getKey(), attributesEntrySet.getValue());
		}
		return bundle;
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
