package com.fo0.twitchbot.utils;

import org.apache.commons.lang3.StringUtils;

import com.fo0.downloader.controller.Controller;

public class StartUpMessage {

	public static void message() {
		System.out.println();
		Logger.info("######################################################");
		Logger.info("    Starting TwitchBot");
		Logger.info("    Version: " + CONSTANTS.VERSION);
		Logger.info("    Author: fo0");
		Logger.info("    GitHub: https://github.com/fo0/TwitchBot.git");
		Logger.info("    Options: " + StringUtils.join(Controller.arg, ", "));
		Logger.info("######################################################");
		System.out.println();
	}
	
}
