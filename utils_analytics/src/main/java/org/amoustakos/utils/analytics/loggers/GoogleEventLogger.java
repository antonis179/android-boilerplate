package org.amoustakos.utils.analytics.loggers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.amoustakos.utils.analytics.EventAction;
import org.amoustakos.utils.analytics.EventLogger;

import java.util.Map;


public class GoogleEventLogger implements EventLogger<Void> {

	private final Tracker tracker;


	// =========================================================================================
	// Constructors
	// =========================================================================================

	public GoogleEventLogger(@NonNull Tracker tracker) {
		this.tracker = tracker;
	}

	// =========================================================================================
	// Logging
	// =========================================================================================

	//TODO
	@Override
	public void logEvent(
			@NonNull String eventName,
			@NonNull String action,
			@Nullable Map<String, String> parameters) {

		if (action.equals(EventAction.VIEWED)) {
			tracker.setScreenName(eventName);
			tracker.send(new HitBuilders.ScreenViewBuilder().build());
		}
		else {
			tracker.send(
					new HitBuilders.EventBuilder()
							.setCategory(eventName)
							.setAction(action).build()
			);
		}
	}

	@Override
	public Void transformParameters(@NonNull Map<String, String> attributes) {
		return null;
	}

	@Override
	public String makeEventName(@NonNull String eventName, @Nullable String action) {
		return null;
	}
}
