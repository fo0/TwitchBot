package com.fo0.twitchbot.bot;

import org.apache.commons.lang3.EnumUtils;

import com.fo0.twitchbot.model.TwitchBotAction;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.Utils;

public class ActionHandler {

	public static void handle(TwitchBotAction action, TwitchBot bot) {
		if (!EnumUtils.isValidEnum(EBotAction.class, action.getAction())) {
			Logger.info("Could not find action: " + action.getAction());
			return;
		}

		try {
			switch (EBotAction.valueOf(action.getAction())) {
			case ChatMessage:
				bot.sendMessageToChannel(action.getValue());
				break;

			case PrivateMessage:
				bot.sendMessageToUser(action.getToUser(), action.getValue());
				break;

			default:
				Logger.info("This aciton is currently not imnplemented: " + action.getAction());
				break;
			}
		} catch (Exception e) {
			Logger.error("Some error occurred at the Actionhandler: " + e);
			Utils.sleep(250);
		}

	}

}
