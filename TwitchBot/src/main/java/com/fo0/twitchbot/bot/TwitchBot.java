package com.fo0.twitchbot.bot;

import java.util.Date;
import java.util.function.Consumer;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.event.channel.ChannelJoinEvent;
import org.kitteh.irc.client.library.event.client.ClientReceiveCommandEvent;
import org.kitteh.irc.client.library.event.helper.MessageEvent;
import org.kitteh.irc.client.library.feature.twitch.TwitchSupport;

import com.fo0.twitchbot.utils.Logger;

import lombok.Data;
import lombok.Getter;
import net.engio.mbassy.listener.Handler;

@Data
public class TwitchBot {

	@Getter
	private String name;
	private String channel;

	private static final String ADDRESS = "irc.chat.twitch.tv.";
	private static final int PORT = 443;
	private String oAuth;

//	private Bot bot = new Bot();
	private Client bot;

	public TwitchBot(String name, String channel, String oAuth) {
		super();
		this.name = name;
		this.channel = channel.toLowerCase();
		this.oAuth = oAuth;

		init();
	}

	public void init() {
//		setName(name);
//		bot.setLoginName(name);

		bot = Client.builder().server().host(ADDRESS).port(PORT).password(oAuth).then().nick(name).build();
	}

	public void connectToTwitch() {
		try {
			TwitchSupport.addSupport(bot);
			bot.getEventManager().registerEventListener(this);
			addChatListener(null);
			bot.connect();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * add this before start the bot
	 * 
	 * @param chatListener
	 */
	public void addChatListener(Consumer<String> chatListener) {
//		bot.setInputListener(e -> {
//			Logger.debug("InputListener: " + e);
//		});
//
//		bot.setOutputListener(e -> {
//			if (StringUtils.contains(e, "oauth")) {
//				Logger.debug("obfuscate oauth key...");
//				return;
//			}
//			Logger.debug("OutputListener: " + e);
//		});
	}

	@Handler
	public void log(ClientReceiveCommandEvent event) {
		System.out.println("We get signal!");
	}

	@Handler
	public void onChatMessage(MessageEvent event) {
		Logger.debug("Chat: " + event.getMessage());
	}

	@Handler
	public void onUserJoinChannel(ChannelJoinEvent event) {
		Logger.debug("User joined: " + event.getUser().getName());
	}

	public void addCapabilities() {
		bot.sendRawLine("CAP REQ :twitch.tv/membership");
		bot.sendRawLine("CAP REQ :twitch.tv/commands");
		bot.sendRawLine("CAP REQ :twitch.tv/tags");
	}

	public void joinChannel() {
		bot.addChannel(getChannel());
	}

	public void leaveChannel() {
		Logger.error("IGNORE: leaveChannel api, not implemented");
	}

	public void sendMessageToChannel(String message) {
		Logger.info("sending message: " + getFormattedMessage(message));
		bot.sendMessage(getChannel(), getFormattedMessage(message));
	}

	public void sendTestMessage() {
		String currentTime = getCurrentTime();
		Logger.info("sending message: my test message: " + currentTime);
		bot.sendMessage(getChannel(), "my test message: " + currentTime);
	}

	private String getFormattedMessage(String message) {
		return String.format("%s", message);
	}

	private String getCurrentTime() {
		return DateFormatUtils.format(new Date(), "HH:mm:ss");
	}

	private String getChannel() {
		return "#" + channel;
	}

}
