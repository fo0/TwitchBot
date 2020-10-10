package com.fo0.twitchbot.bot.template;

import java.util.HashMap;

import com.fo0.twitchbot.bot.pool.BotActionPool;
import com.fo0.twitchbot.model.BotActionConfig;
import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.ConfigLoader;
import com.fo0.twitchbot.utils.Logger;

import lombok.Getter;
import lombok.Setter;

public class ActionDefaultTemplate {

	public static String CONFIG_PATH = CONSTANTS.CONFIG_FOLDER_PATH + "/template/actions.json";

	@Getter
	@Setter
	//@formatter:off
	public static BotActionConfig CONFIG = BotActionConfig.builder().map(new HashMap<String, String>() {
				private static final long serialVersionUID = -7044969472834005056L;
				{
					// TODO: check if the replacement is only for the whole word
					// not maybe just for a port of it
					put("help", "Die aktuell verfügbaren Actions sind: $actions");
					put("time", "Es ist $time");
					put("faq", "Die aktuell verfügbaren FAQ sind: $faq");
				}
			})
			.build();
	//@formatter:on
	
	private static ConfigLoader<BotActionConfig> loader = null;

	public static void init() {
		Logger.debug("checking for action templates...");
		loader = new ConfigLoader<BotActionConfig>(
				"action-config",
				CONFIG_PATH,
				BotActionConfig.class,
				ActionDefaultTemplate::getCONFIG,
				ActionDefaultTemplate::setCONFIG);

		loader.setLoadListener(e -> {
			// update botactions every load
			BotActionPool.addEntries(e.getMap());
		});

		loader.load();
	}

	public static void reloadTemplate() {
		loader.load();
	}

}
