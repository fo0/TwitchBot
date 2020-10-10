package com.fo0.twitchbot.test;

import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Ignore;

import com.fo0.twitchbot.bot.TwitchBot;
import com.fo0.twitchbot.utils.AuthFromFile;

@Ignore
public class ConnectTest {

	private String name = "fo0mebot";
	private String channel = "fo0me";
	private static String oAuth = "";

	@BeforeClass
	public static void beforeClass() {
		oAuth = AuthFromFile.getTwitchOauthKey();
	}

	@org.junit.Test
	public void test() {
		TwitchBot bot = new TwitchBot(name, channel, oAuth);
		bot.connectToTwitch();
		bot.addCapabilities();
		bot.addDefaultChannel();
		bot.leaveChannel();

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
