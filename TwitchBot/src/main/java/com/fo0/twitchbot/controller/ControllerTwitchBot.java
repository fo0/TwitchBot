package com.fo0.twitchbot.controller;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.bot.TwitchBotManager;
import com.fo0.twitchbot.config.twitchbots.TwitchBotConfigLoader;
import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.utils.AuthFromFile;
import com.fo0.twitchbot.utils.Logger;
import com.google.common.collect.Maps;

public class ControllerTwitchBot {

	private static final Map<String, TwitchBotManager> sessions = Maps.newHashMap();

	public static void init() {
		Logger.info("starting controller: twitchbot");

		loadConfig();
	}

	public static void loadConfig() {
		TwitchBotConfigLoader.init();

		TwitchBotConfigLoader.getBots().getList().stream().forEach(e -> {
			// check if oauth file was found
			// adding it to the bot oauth
			e.setOauth(AuthFromFile.getTwitchOauthKey());
			addBot(e);
		});
	}

	public static TwitchBotManager getBotByID(String id) {
		return sessions.get(id);
	}

	public static TwitchBotManager getBotByName(String name) {
		return sessions.entrySet().stream().filter(e -> StringUtils.equals(e.getValue().getConfig().getName(), name))
				.map(Entry::getValue).findFirst().orElse(null);
	}

	public static void addBot(TwitchBotConfig config) {
		Logger.info("Adding Bot with ID: " + config.getId() + " [" + config.getName() + "|" + config.getChannel() + "]");
		sessions.put(config.getId(), new TwitchBotManager(config));
	}

	public static void removeBot(String id) {
		Logger.info("Removing Bot with ID: " + id);
		sessions.remove(id);
	}

}
