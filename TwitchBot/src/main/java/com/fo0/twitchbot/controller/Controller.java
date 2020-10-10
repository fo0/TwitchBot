package com.fo0.twitchbot.controller;

import com.fo0.twitchbot.api.cmd.CommandlineApi;
import com.fo0.twitchbot.config.cmd.Config;
import com.fo0.twitchbot.config.cmd.Configuration;
import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.startup.StartUpMessage;
import com.fo0.twitchbot.utils.AuthFromFile;
import com.fo0.twitchbot.utils.Logger;

public class Controller {

	public static String[] arg;
	public static Config config = null;
	public static CommandlineApi cmdApi = null;

	public static void bootstrap(String[] args) {
		Logger.debug("starting controller");

		arg = args;

		StartUpMessage.init();

		Configuration.init();

		modules();

//		addDefaults();
	}

	public static void preConstruct() {
		// disable ipv6
		System.setProperty("java.net.preferIPv4Stack", "true");
	}

	public static void modules() {
		ControllerSystem.init();
		ControllerRest.init();
		ControllerTwitchBot.init();
	}

	public static void enableCommandlineApi() {
		Logger.info("Starting CommandLineApi");
		cmdApi = new CommandlineApi();
	}

	public static void addDefaults() {
		ControllerTwitchBot.addBot(TwitchBotConfig.builder()
				.id("fo0mebot").name("fo0mebot")
				.oauth(AuthFromFile.getTwitchOauthKey())
				.channel("fo0me").build());
	}
}
