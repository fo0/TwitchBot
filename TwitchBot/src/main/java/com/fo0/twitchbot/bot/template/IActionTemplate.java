package com.fo0.twitchbot.bot.template;

import java.util.Map;

public interface IActionTemplate {

	public void addEntry(String key, String value);
	
	public void addEntries(Map<String, String> map);
}
