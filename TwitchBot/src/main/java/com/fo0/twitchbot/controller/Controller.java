package com.fo0.twitchbot.controller;

import com.fo0.twitchbot.api.cmd.CommandlineApi;
import com.fo0.twitchbot.config.cmd.Config;
import com.fo0.twitchbot.config.cmd.Configuration;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.StartUpMessage;

public class Controller {

	public static String[] arg;

	public static Config config = null;

	public static CommandlineApi cmdApi = null;

	public static void bootstrap(String[] args) {
		Logger.debug("starting controller");

		arg = args;

		StartUpMessage.message();

		Configuration.init();

		modules();

		addDefaults();
	}

	public static void modules() {
		ControllerTwitchBot.init();
	}

	public static void enableCommandlineApi() {
		Logger.info("Enabled the CommandLineApi");
		cmdApi = new CommandlineApi();
	}

	public static void addDefaults() {
//		ControllerTwitchBot.addBot(TwitchBotConfig.builder()
//				.id("fo0mebot")
//				.name("fo0mebot")
//				.oauth(AuthFromFile.getTwitchOauthKey())
//				.channel("fo0me").build());
	}
}
