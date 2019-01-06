package com.fo0.twitchbot.utils;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class Utils {

	public static void sleep(long sleep) {
		sleep(TimeUnit.MILLISECONDS, sleep);
	}
	
	public static void sleep(TimeUnit unit, long sleep) {
		Logger.debug("Sleeping " + DurationFormatUtils.formatDuration(unit.toMillis(sleep), "HH:mm:ssS"));
		try {
			unit.sleep(sleep);
		} catch (InterruptedException e) {
		}
	}
	
}
