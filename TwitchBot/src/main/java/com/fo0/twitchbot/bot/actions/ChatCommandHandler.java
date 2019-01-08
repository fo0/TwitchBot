package com.fo0.twitchbot.bot.actions;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.fo0.twitchbot.bot.EBotAction;
import com.fo0.twitchbot.bot.TwitchBotManager;
import com.fo0.twitchbot.model.TwitchBotAction;
import com.fo0.twitchbot.utils.Logger;

public class ChatCommandHandler {

	public ChatCommandHandler(String chatCommand, TwitchBotManager manager) {
		Logger.debug("Evaluating ChatCommand: " + chatCommand);
		switch (chatCommand) {
		case "!zeit":
		case "!time":
			manager.addAction(TwitchBotAction.builder().action(EBotAction.Message.name())
					.value("Es ist: " + getCurrentTime()).build());
			break;

		default:
			Logger.debug("IGNORE: Not implemented ChatCommand: " + chatCommand);
			break;
		}
	}

	public String getCurrentTime() {
		return DateFormatUtils.format(new Date(), "HH:mm:ss");
	}

}
