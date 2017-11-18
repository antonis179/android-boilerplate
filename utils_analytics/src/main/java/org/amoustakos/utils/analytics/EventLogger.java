package org.amoustakos.utils.analytics;

import org.amoustakos.utils.analytics.EventAction;

import java.util.Map;


/**
 * @param <T> The parameters type to be used for logging
 */
public interface EventLogger<T> {

	String SPACE = " ";

	void logEvent(
			String eventName,
			@EventAction String action,
			Map<String, String> parameters
	);

	T transformParameters(Map<String, String> attributes);

	String makeEventName(String eventName, @EventAction String action);
}
