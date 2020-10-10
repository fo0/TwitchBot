package com.fo0.twitchbot.startup;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.controller.Controller;
import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.Logger;

public class StartUpMessage {

	public static void init() {
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
