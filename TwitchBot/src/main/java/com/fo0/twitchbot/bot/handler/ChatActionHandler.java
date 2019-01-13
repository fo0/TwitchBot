package com.fo0.twitchbot.bot.handler;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.bot.EBotAction;
import com.fo0.twitchbot.bot.TwitchBotManager;
import com.fo0.twitchbot.model.TwitchBotAction;
import com.fo0.twitchbot.model.TwitchChatMessage;
import com.fo0.twitchbot.utils.Logger;

public class ChatActionHandler {

	public static void handle(TwitchChatMessage msg, TwitchBotManager manager) {
		if (!isValidCommand(msg.getMessage())) {
			Logger.trace("Invalid Chat Command: " + msg.getName() + " -> \"" + msg.getMessage() + "\"");
			return;
		}

		String message = BotAction.execute(String.valueOf(msg.getMessage()));

		if (StringUtils.isEmpty(message)) {
			Logger.debug("No action messge found for: " + msg.getMessage());
			return;
		}

		Logger.info("sending Answer of " + highlight(msg.getName()) + ": " + highlight(msg.getMessage()) + " --> "
				+ highlight(message));
		
		manager.addAction(TwitchBotAction.builder().action(EBotAction.ChatMessage.name()).value(message).build());
	}

	public static String highlight(String str) {
		return "\"" + str + "\"";
	}

	public static boolean isValidCommand(String command) {
		if (StringUtils.isBlank(command)) {
			return false;
		}

		return StringUtils.startsWith(command, "!");
	}
}
