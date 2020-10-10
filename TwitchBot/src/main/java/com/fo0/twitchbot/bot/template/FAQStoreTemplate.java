package com.fo0.twitchbot.bot.template;

import com.fo0.twitchbot.model.FAQConfig;
import com.fo0.twitchbot.model.FAQItem;
import com.fo0.twitchbot.model.QuestionStore;
import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.ConfigLoader;
import com.fo0.twitchbot.utils.Logger;
import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;

public class FAQStoreTemplate {

	public static String CONFIG_PATH = CONSTANTS.CONFIG_FOLDER_PATH + "/template/faq.json";

	@Getter
	@Setter
	public static FAQConfig CONFIG = FAQConfig.builder()
	                                          .list(Lists.newArrayList(
	                                                                   FAQItem.builder()
	                                                                          .question(
	                                                                                    new QuestionStore("Hallo"))
	                                                                          .answer("Moin Moin :-)")
	                                                                          .build()))
	                                          .build();

	private static ConfigLoader<FAQConfig> loader = null;

	public static void init() {
		Logger.debug("checking for faq templates...");
		loader = new ConfigLoader<FAQConfig>("faq-config", CONFIG_PATH, FAQConfig.class, FAQStoreTemplate::getCONFIG, FAQStoreTemplate::setCONFIG);
	}

	public static void reloadTemplate() {
		loader.load();
	}
}
