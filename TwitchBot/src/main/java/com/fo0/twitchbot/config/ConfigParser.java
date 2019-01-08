package com.fo0.twitchbot.config;

import java.util.Collections;

import com.fo0.twitchbot.controller.Controller;
import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.Logger;
import com.google.devtools.common.options.OptionsParser;

public class ConfigParser {

	public static Config parseConfig(String[] args) {
		OptionsParser parser = OptionsParser.newOptionsParser(Config.class);
		if (CONSTANTS.DEBUG) {
			printUsage(parser);
		}

		parser.parseAndExitUponError(args);
		return parser.getOptions(Config.class);
	}

	public static void printUsage(OptionsParser parser) {
		System.out.println();
		Logger.info("Usage: java -jar twitchbot.jar <OPTIONS>");
		Logger.info(parser.describeOptions(Collections.<String, String>emptyMap(), OptionsParser.HelpVerbosity.LONG));
		System.out.println();
	}

	public static void applyConfig() {
		CONSTANTS.DEBUG = Controller.config.debug;
	}

}
