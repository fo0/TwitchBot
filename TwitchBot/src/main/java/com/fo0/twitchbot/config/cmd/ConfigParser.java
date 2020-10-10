package com.fo0.twitchbot.config.cmd;

import java.util.Collections;
import java.util.List;

import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.Logger;
import com.google.devtools.common.options.OptionsParser;

public class ConfigParser {

	public static Config parseConfig(List<String> args) {
		OptionsParser parser = OptionsParser.newOptionsParser(Config.class);
		if (CONSTANTS.DEBUG) {
			printUsage(parser);
		}

		parser.parseAndExitUponError(args.stream().toArray(String[]::new));
		return parser.getOptions(Config.class);
	}

	public static void printUsage(OptionsParser parser) {
		System.out.println();
		Logger.info("Usage: java -jar twitchbot.jar <OPTIONS>");
		Logger.info(parser.describeOptions(Collections.<String, String>emptyMap(), OptionsParser.HelpVerbosity.LONG));
		System.out.println();
	}

}
