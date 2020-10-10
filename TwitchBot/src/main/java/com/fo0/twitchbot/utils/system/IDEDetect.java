package com.fo0.twitchbot.utils.system;

public class IDEDetect {

	public static boolean inIDE() {
		return System.getProperty("java.class.path").contains("/target/classes") ? true : false;
	}

}
