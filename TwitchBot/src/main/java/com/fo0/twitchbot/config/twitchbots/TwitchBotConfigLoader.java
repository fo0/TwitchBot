package com.fo0.twitchbot.config.twitchbots;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.model.TwitchBotConfigs;
import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.ConfigLoader;
import com.fo0.twitchbot.utils.Logger;

import lombok.Getter;
import lombok.Setter;

public class TwitchBotConfigLoader {

	@Getter
	@Setter
	private static TwitchBotConfigs bots = TwitchBotConfigs.builder().build();

	private static ConfigLoader<TwitchBotConfigs> loader = null;

	public static void init() {
		loader = new ConfigLoader<TwitchBotConfigs>(CONSTANTS.TWITCH_BOT_CONFIG, TwitchBotConfigs.class,
				TwitchBotConfigLoader::getBots, TwitchBotConfigLoader::setBots);

		// init load config from file
		load();
	}

	public static void save() {
		loader.save();
	}

	public static void load() {
		loader.load();
	}

	public static void addBot(TwitchBotConfig config) {
		// check for id or name already exists
		if (bots.getList().stream().anyMatch(e -> {
			if (StringUtils.equals(e.getId(), config.getId()))
				return true;

			if (StringUtils.equals(e.getName(), config.getName()))
				return true;

			return false;
		})) {
			Logger.debug("bot already exists");
			return;
		}

		bots.getList().add(config);
		
		save();
	}
}
