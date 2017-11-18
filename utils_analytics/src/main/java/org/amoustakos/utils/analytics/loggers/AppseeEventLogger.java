package org.amoustakos.utils.analytics.loggers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.appsee.Appsee;

import org.amoustakos.utils.analytics.EventAction;
import org.amoustakos.utils.analytics.EventLogger;

import java.util.HashMap;
import java.util.Map;



public class AppseeEventLogger implements EventLogger<Map<String, Object>> {

	// =========================================================================================
	// Constructors
	// =========================================================================================

	public AppseeEventLogger() {}

	// =========================================================================================
	// Logging
	// =========================================================================================

	@Override
	public void logEvent(
			@NonNull String eventName,
			@Nullable String action,
			@NonNull Map<String, String> parameters) {
		Appsee.addEvent(makeEventName(eventName, action), transformParameters(parameters));
	}


	@Override
	public Map<String, Object> transformParameters(@NonNull Map<String, String> attributes) {
		Map<String, Object> map = new HashMap<>();
		for (Map.Entry<String, String> attributesEntrySet : attributes.entrySet()) {
			map.put(attributesEntrySet.getKey(), attributesEntrySet.getValue());
		}
		return map;
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