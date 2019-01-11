package com.fo0.twitchbot.bot.template;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.fo0.twitchbot.bot.handler.BotAction;

public class VariableDefaultTemplate {

	public static Map<String, Supplier<String>> TEMPLATE = new HashMap<String, Supplier<String>>() {
		private static final long serialVersionUID = -5980361684319237858L;
		{
			put("time", () -> getCurrentTime());
			put("actions", () -> actions());
		}
	};

	public static String actions() {
		return StringUtils
				.join(BotAction.ACTIONS.keySet().parallelStream().map(e -> "!" + e).collect(Collectors.toList()), ", ");
	}

	public static String getCurrentTime() {
		return DateFormatUtils.format(new Date(), "HH:mm:ss");
	}
}
