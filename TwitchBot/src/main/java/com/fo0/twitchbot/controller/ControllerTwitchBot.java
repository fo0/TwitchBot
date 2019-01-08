package com.fo0.twitchbot.controller;

import java.util.Map;

import com.fo0.twitchbot.bot.TwitchBotManager;
import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.utils.Logger;
import com.google.common.collect.Maps;

public class ControllerTwitchBot {

	private static final Map<String, TwitchBotManager> sessions = Maps.newHashMap();

	public static void init() {
		Logger.info("starting controller: twitchbot");
	}

	public static TwitchBotManager getBot(String id) {
		return sessions.get(id);
	}
	
	public static void addBot(TwitchBotConfig config) {
		Logger.info("Adding Bot with ID: " + config.getId() + " [" + config.getName() + "]");
		sessions.put(config.getId(), new TwitchBotManager(config));
	}

	public static void removeBot(String id) {
		Logger.info("Removing Bot with ID: " + id);
		sessions.remove(id);
	}

}
