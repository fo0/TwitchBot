package com.fo0.twitchbot.bot.actions;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.fo0.twitchbot.bot.EBotAction;
import com.fo0.twitchbot.bot.TwitchBotManager;
import com.fo0.twitchbot.model.TwitchBotAction;
import com.fo0.twitchbot.model.TwitchChatMessage;
import com.fo0.twitchbot.utils.Logger;

public class ChatCommandHandler {

	public static void handle(TwitchChatMessage msg, TwitchBotManager manager) {
		if (!isValidCommand(msg.getMessage())) {
			Logger.trace("Invalid Chat Command: " + msg.getName() + " -> \"" + msg.getMessage() + "\"");
			return;
		}

		Logger.debug("Evaluating Chat Command: " + msg.getName() + " -> \"" + msg.getMessage() + "\"");
		switch (msg.getMessage().toLowerCase()) {
		case "!zeit":
		case "!time":
			manager.addAction(TwitchBotAction.builder().action(EBotAction.Message.name())
					.value("Es ist: " + getCurrentTime() + " Uhr").build());
			break;

		default:
			Logger.debug("IGNORE: Not implemented ChatCommand: " + msg.getMessage());
			break;
		}
	}

	public static String getCurrentTime() {
		return DateFormatUtils.format(new Date(), "HH:mm:ss");
	}

	public static boolean isValidCommand(String command) {
		if (StringUtils.isBlank(command)) {
			return false;
		}

		return StringUtils.startsWith(command, "!");
	}
}
