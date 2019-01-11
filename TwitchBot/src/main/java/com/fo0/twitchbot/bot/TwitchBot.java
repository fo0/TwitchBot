package com.fo0.twitchbot.bot;

import java.util.Date;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.event.channel.ChannelJoinEvent;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.feature.twitch.TwitchSupport;

import com.fo0.twitchbot.model.TwitchChatMessage;
import com.fo0.twitchbot.utils.Logger;

import lombok.Data;
import lombok.Getter;
import net.engio.mbassy.listener.Handler;

/**
 * wrapper for https://kitteh.org/irc-client-library/
 * 
 * @author fo0
 */
@Data
public class TwitchBot {

	@Getter
	private String name;
	private String channel;

	private static final String ADDRESS = "irc.chat.twitch.tv.";
	private static final int PORT = 443;
	private String oAuth;

	private Client bot;

	private Consumer<TwitchChatMessage> chatListener;

	private Consumer<String> userJoinListener;

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
	public void addChatListener(Consumer<TwitchChatMessage> chatListener) {
		this.chatListener = chatListener;
	}

	/**
	 * add this before start the bot
	 * 
	 * @param chatListener
	 */
	public void addUserJoinListener(Consumer<String> userJoinListener) {
		this.userJoinListener = userJoinListener;
	}

	@Handler
	public void onChatMessage(ChannelMessageEvent event) {
		TwitchChatMessage msg = TwitchChatMessage.builder().name(event.getActor().getNick()).message(event.getMessage())
				.build();

		Logger.debug(String.format("Chat [%s] %s", msg.getName(), msg.getMessage()));

		if (chatListener != null && StringUtils.isNotBlank(msg.getMessage())) {
			chatListener.accept(msg);
		}
	}

	@Handler
	public void onUserJoinChannel(ChannelJoinEvent event) {
		Logger.debug("User joined: " + event.getUser().getName());

		if (userJoinListener != null) {
			userJoinListener.accept(event.getUser().getName());
		}
	}

	public void addCapabilities() {
		bot.sendRawLine("CAP REQ :twitch.tv/membership");
		bot.sendRawLine("CAP REQ :twitch.tv/commands");
		bot.sendRawLine("CAP REQ :twitch.tv/tags");
	}

	public void addDefaultChannel() {
		Logger.debug("add channel: #" + channel);
		bot.addChannel(getChannel());
		joinChannel(channel);
	}

	public void addChannel(String channel) {
		Logger.debug("add channel: #" + channel);
		bot.addChannel("#" + channel);
		joinChannel(channel);
	}

	public void joinChannel(String channel) {
		Logger.debug("join channel: #" + channel);
		sendCustomCommand("#" + channel);
	}

	public void sendCustomCommand(String command) {
		if (StringUtils.isBlank(command)) {
			Logger.debug("Empty command not allowed");
			return;
		}

		bot.sendRawLineImmediately("JOIN " + command);
	}

	public void leaveChannel() {
		Logger.error("IGNORE: leaveChannel api, not implemented");
	}

	public void sendMessageToChannel(String message) {
		Logger.info("sending message to channel: " + getFormattedMessage(message));
		bot.sendMessage(getChannel(), getFormattedMessage(message));
	}

	public void sendMessageToUser(String user, String message) {
		Logger.info("sending private-message to user: " + user + " -> " + getFormattedMessage(message));
		bot.sendMessage(user, getFormattedMessage(message));
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

	// TODO:
//	@Handler
//	public void log(ClientReceiveCommandEvent event) {
//		Logger.debug(String.format("Event: %s, %s", event.getClient().getName(), event.getOriginalMessage()));
//	}

	/**
	 * triggers on events like: <br>
	 * * pong
	 * 
	 * @param event
	 */
//	@Handler
//	public void clientReceiveCommandEvent(ClientReceiveCommandEvent event) {
//		Logger.debug("Client command: " + event.getOriginalMessage());
//	}
//
//	@Handler
//	public void clientReceiveNumericEvent(ClientReceiveNumericEvent event) {
//		Logger.debug("Client numeric: " + event.getOriginalMessage());
//	}

}
