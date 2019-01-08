package com.fo0.twitchbot.test;

import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;

import com.fo0.twitchbot.bot.TwitchBot;
import com.fo0.twitchbot.utils.OAuthFromFile;

public class ConnectTest {

	private String name = "fo0mebot";
	private String channel = "fo0me";
	private static String oAuth = "";

	@BeforeClass
	public static void beforeClass() {
		oAuth = OAuthFromFile.getKey();
	}

	@org.junit.Test
	public void test() {
		TwitchBot bot = new TwitchBot(name, channel, oAuth);
		bot.connectToTwitch();
		bot.addCapabilities();
		bot.joinChannel();
		bot.sendTestMessage();
		bot.leaveChannel();

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
