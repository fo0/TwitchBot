package com.fo0.twitchbot.bot;

import java.util.function.Consumer;

import org.jibble.pircbot.PircBot;

import com.fo0.twitchbot.utils.Logger;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Bot extends PircBot {

	@Setter
	private Consumer<String> chatListener;

	public void setLoginName(String loginName) {
		setName(loginName);
		setLogin(loginName);
	}

	@Override
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		Logger.debug("Chat: " + message);
		super.onMessage(channel, sender, login, hostname, message);

		if (chatListener != null) {
			chatListener.accept(message);
		}
	}
	
//	@Override
//	protected void handleLine(String line) {
//		super.handleLine(line);
//
//		if (chatListener != null) {
//			Logger.debug("Chat: " + line);
//			chatListener.accept(line);
//		}
//	}
}
