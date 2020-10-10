package com.fo0.twitchbot.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fo0.twitchbot.bot.TwitchBotManager;
import com.fo0.twitchbot.config.twitchbots.TwitchBotConfigLoader;
import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.utils.AuthFromFile;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.system.IDEDetect;
import com.google.common.collect.Maps;

import lombok.Getter;

@Component
public class ControllerTwitchBot {

	@Getter
	private static final Map<String, TwitchBotManager> SESSIONS = Maps.newHashMap();

	@PostConstruct
	public void init() {
		Logger.info("starting controller: twitchbot");

		loadConfig();
	}

	public void loadConfig() {
		TwitchBotConfigLoader.init();

		TwitchBotConfigLoader.getBots().getList().stream().forEach(bot -> {
			addBot(bot, false);
		});
	}

	public TwitchBotManager getBotByID(String id) {
		return SESSIONS.get(id);
	}

	public TwitchBotManager getBotByName(String name) {
		return SESSIONS.entrySet()
				.stream()
				.filter(e -> StringUtils.equals(e.getValue().getConfig().getName(), name))
				.map(Entry::getValue)
				.findFirst()
				.orElse(null);
	}

	public void addBot(TwitchBotConfig config) {
		addBot(config, true);
	}

	public void addBot(TwitchBotConfig config, boolean save) {
		if (getBotByName(config.getName()) != null) {
			Logger.error(String.format("Cannot add Bot with Name '%s' already exists", config.getName()));
			return;
		}

		Logger.info("Adding Bot with ID: " + config.getId() + " [" + config.getName() + "|" + config.getChannel() + "]");

		// TODO: remove: load oauth from file
		if (IDEDetect.inIDE()) {
			AuthFromFile.addTwitchOAuthKeyToConfig(config);
		}

		SESSIONS.put(config.getId(), new TwitchBotManager(config));

		if (save) {
			TwitchBotConfigLoader.addBot(config);
		}
	}

	public void removeBot(String id) {
		Logger.info("Removing Bot with ID: " + id);
		SESSIONS.remove(id);
	}

	public Collection<TwitchBotConfig> getSessionConfigs() {
		return SESSIONS.entrySet().stream().map(e -> e.getValue().getConfig()).collect(Collectors.toList());
	}

}
