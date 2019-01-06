package com.fo0.twitchbot.bot;

import org.jibble.pircbot.PircBot;

public class TwitchBot extends PircBot {

	private String name;
	private String channel;

	private String oAuth;

	private static final String ADDRESS = "irc.twitch.tv.";

	private static final int PORT = 6667;

	public TwitchBot(String name, String channel, String oAuth) {
		super();
		this.name = name;
		this.channel = channel.toLowerCase();
		this.oAuth = oAuth;
		
		setName(name);
		setLogin(name);
		
	}

	public void connectToTwitch() {
		try {
			connect(ADDRESS, PORT, oAuth);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

	}
	
	public void addCapabilities() {
		sendRawLine("CAP REQ :twitch.tv/membership");
		sendRawLine("CAP REQ :twitch.tv/commands");
		sendRawLine("CAP REQ :twitch.tv/tags");
	}

	public void joinChannelFo0Me() {
		joinChannel("#"+channel);
	}

	public void leaveChannelFo0Me() {
		System.err.println("IGNORE: cannot be found in irc api");
	}

	public void sendTestMessage() {
		System.out.println("sending message: my test message");
		sendMessage("#"+channel, "my test message");
	}

}
