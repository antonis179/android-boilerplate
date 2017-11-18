package org.amoustakos.utils.analytics;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;
import static org.amoustakos.utils.analytics.EventAction.*;


@Retention(SOURCE)
@StringDef({
		VIEWED, PRESS, LINK_SELECTED, NONE
})
public @interface EventAction {
	String

	VIEWED = "Viewed",
	PRESS = "Pressed",
	LINK_SELECTED = "Link Selection",

	NONE = "";


}
