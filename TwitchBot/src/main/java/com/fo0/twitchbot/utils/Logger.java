package com.fo0.twitchbot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private static final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

	public static void info(String msg) {
		System.out.println(timestamp() + " [INFO] " + msg);
	}

	public static void debug(String msg) {
		if (CONSTANTS.DEBUG) {
			System.out.println(timestamp() + " [DEBUG] " + msg);
		}
	}

	public static void trace(String msg) {
		if (CONSTANTS.TRACE) {
			System.out.println(timestamp() + " [TRACE] " + msg);
		}
	}

	public static void error(String msg) {
		System.err.println(timestamp() + " [ERROR] " + msg);
	}

	private static String timestamp() {
		return df.format(new Date());
	}
}
