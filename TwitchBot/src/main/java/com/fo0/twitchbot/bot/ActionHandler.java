package com.fo0.twitchbot.bot;

import com.fo0.twitchbot.model.TwitchBotAction;
import com.fo0.twitchbot.utils.Logger;

public class ActionHandler {

	public static void handle(TwitchBotAction action, TwitchBot bot) {
		switch (action.getAction()) {
		case "Message":
			bot.sendMessageToChannel(action.getValue());
			break;

		default:
			Logger.info("Could not find action: " + action.getAction());
			break;
		}
	}

}
