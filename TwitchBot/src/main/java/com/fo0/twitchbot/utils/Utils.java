package com.fo0.twitchbot.utils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import com.google.common.collect.Lists;

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

	public static String getCurrentTime() {
		return DateFormatUtils.format(new Date(), "HH:mm:ss");
	}

	public static List<String> mapByRegEx(String str, String regEx) {
		Pattern pattern = Pattern.compile("\\$\\w+");
		Matcher m = pattern.matcher(str);
		List<String> list = Lists.newArrayList();
		while (m.find()) {
			// possible variable
			list.add(m.group());
		}
		return list;
	}

}
