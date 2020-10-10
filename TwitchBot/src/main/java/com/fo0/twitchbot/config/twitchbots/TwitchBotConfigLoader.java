package com.fo0.twitchbot.config.twitchbots;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.model.TwitchBotConfigs;
import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.ConfigLoader;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.system.IDEDetect;

import lombok.Getter;
import lombok.Setter;

public class TwitchBotConfigLoader {

	public static String CONFIG_PATH = CONSTANTS.CONFIG_FOLDER_PATH + "/bots/twitchbots.json";

	@Getter
	@Setter
	private static TwitchBotConfigs bots = TwitchBotConfigs.builder().build();

	private static ConfigLoader<TwitchBotConfigs> loader = null;

	public static void init() {
		loader = new ConfigLoader<TwitchBotConfigs>("bot-config", CONFIG_PATH, TwitchBotConfigs.class,
				TwitchBotConfigLoader::getBots, TwitchBotConfigLoader::setBots);

		// init load config from file
		load();
	}

	public static void save() {
		// Remove twitch oauth key
		// TODO: this is only for dev!
		if (IDEDetect.inIDE()) {
			loader.getInput().get().getList().stream().forEach(e -> e.setOauth("-removed-"));
		}

		loader.save();
	}

	public static void load() {
		loader.load();

		// adding oauth key
		// TODO: this is only for dev
		// loader.getInput().get().getList().stream().forEach(AuthFromFile::addTwitchOAuthKeyToConfig);

		if (bots == null) {
			bots = TwitchBotConfigs.builder().build();
		}
	}

	public static void addBot(TwitchBotConfig config) {
		// check for id or name already exists
		if (bots.getList().stream().anyMatch(e -> {
			if (StringUtils.equals(e.getId(), config.getId())) {
				return true;
			}

			if (StringUtils.equals(e.getName(), config.getName())) {
				return true;
			}

			return false;
		})) {
			Logger.debug("bot already exists");
			return;
		}

		bots.getList().add(config);

		save();
	}

}
