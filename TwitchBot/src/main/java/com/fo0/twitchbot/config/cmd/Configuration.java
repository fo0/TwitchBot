package com.fo0.twitchbot.config.cmd;

import com.fo0.twitchbot.bot.template.ActionDefaultTemplate;
import com.fo0.twitchbot.bot.template.FAQStoreTemplate;
import com.fo0.twitchbot.controller.Controller;
import com.fo0.twitchbot.utils.Logger;

public class Configuration {

	private static final String[] args = Controller.arg;

	public static void init() {
		Logger.debug("starting configuration");

		Controller.config = ConfigParser.parseConfig(args);

		ConfigParser.applyConfig();
		
		ActionDefaultTemplate.init();
		
		FAQStoreTemplate.init();
	}

}
