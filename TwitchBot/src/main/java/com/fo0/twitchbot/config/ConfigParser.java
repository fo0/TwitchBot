package com.fo0.twitchbot.config;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.bot.handler.BotAction;
import com.fo0.twitchbot.controller.Controller;
import com.fo0.twitchbot.model.KeyValue;
import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.FileReader;
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

		if (StringUtils.isNotBlank(Controller.config.actionTemplate)) {
			// applying actions to template
			if (Paths.get(Controller.config.actionTemplate).toFile().exists()) {
				List<KeyValue> list = FileReader.read(Paths.get(Controller.config.actionTemplate));
				Map<String, String> map = list.stream().collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue));
				Logger.info("Importing BotActions: " + map.size());
				BotAction.ACTIONS.putAll(map);
			} else {
				Logger.error("Could not read action template on path: "
						+ Paths.get(Controller.config.actionTemplate).toAbsolutePath());
			}
		}

		if (StringUtils.isNotBlank(Controller.config.variableTemplate)) {
			// applying variables to template
		}
	}

}
