package com.fo0.twitchbot.config;

import com.fo0.twitchbot.controller.Controller;
import com.fo0.twitchbot.utils.Logger;

public class Configuration {

	private static final String[] args = Controller.arg;

	public static void bootstrap() {
		Logger.debug("starting configuration");

		ConfigParser.parseConfig(args);

		ConfigParser.applyConfig();
	}

}
