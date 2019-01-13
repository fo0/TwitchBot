package com.fo0.twitchbot.bot.template;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fo0.twitchbot.bot.handler.BotAction;
import com.fo0.twitchbot.controller.Controller;
import com.fo0.twitchbot.model.KeyValue;
import com.fo0.twitchbot.utils.FileReader;
import com.fo0.twitchbot.utils.Logger;

public class ActionDefaultTemplate {

	public static Map<String, String> TEMPLATE = new HashMap<String, String>() {
		private static final long serialVersionUID = -7044969472834005056L;

		{
			put("time", "Es ist $time");

			// TODO: check if the replacement is only for the whole word
			// not maybe just for a port of it
			put("help", "Die aktuell verfügbaren Actions sind: $actions");
		}
	};

	public static void loadFromLocalPath(String path) {
		Logger.info("Reloading Action Template on path: " + Paths.get(path).toAbsolutePath());
		if (Paths.get(path).toFile().exists()) {
			List<KeyValue> list = FileReader.read(Paths.get(path));
			Map<String, String> map = list.stream().collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue));
			Logger.info("Importing BotActions: " + map.size());
			
			// clear and fill with new actions
			BotAction.ACTIONS.clear();
			BotAction.ACTIONS.putAll(TEMPLATE);
			BotAction.ACTIONS.putAll(map);
		} else {
			Logger.error("Could not read action template on path: "
					+ Paths.get(Controller.config.actionTemplate).toAbsolutePath());
		}
	}
}
