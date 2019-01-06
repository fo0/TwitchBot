package com.fo0.downloader.controller;

import com.fo0.twitchbot.config.Config;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.StartUpMessage;

public class Controller {

	public static String[] arg;

	public static Config config = null;

	public static void bootstrap(String[] args) {
		Logger.debug("starting controller");

		arg = args;

		StartUpMessage.message();

		modules();
	}

	public static void modules() {
	}
}
