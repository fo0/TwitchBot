package com.fo0.twitchbot.bot.template;

import java.util.HashMap;
import java.util.Map;

public class ActionDefaultTemplate {

	public static Map<String, String> TEMPLATE = new HashMap<String, String>() {
		private static final long serialVersionUID = -7044969472834005056L;

		{
			put("time", "Es ist $time");
			put("help", "Die aktuell verfügbaren Actions sind: $actions");
		}
	};

}
