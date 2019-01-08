package com.fo0.twitchbot.bot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.fo0.twitchbot.bot.actions.ChatCommandHandler;
import com.fo0.twitchbot.model.TwitchBotAction;
import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.Utils;
import com.google.common.collect.Queues;

public class TwitchBotManager {

	private TwitchBotConfig config;
	private TwitchBot bot;

	private ExecutorService actionListener = Executors.newSingleThreadExecutor();

	private LinkedBlockingQueue<TwitchBotAction> actionQueue = Queues.newLinkedBlockingQueue();

	public TwitchBotManager(TwitchBotConfig config) {
		this.config = config;

		init();

		if (config.isAutostart())
			start();
	}

	private void init() {
		bot = new TwitchBot(config.getName(), config.getChannel(), config.getOauth());
	}

	public void start() {
		if (config.isAllowChatCommands()) {
			addChatListener();
		}

		bot.connectToTwitch();

		addCapabilities();

		startActionListener();

		Utils.sleep(TimeUnit.SECONDS, 2);

		startUpMessage();

		bot.addDefaultChannel();
	}

	public void stop() {
		bot.leaveChannel();
		actionListener.shutdownNow();
		actionQueue.clear();
	}

	private void addCapabilities() {
		bot.addCapabilities();
	}

	private void addChatListener() {
		bot.addChatListener(e -> {
			ChatCommandHandler.handle(e, this);
		});
	}

	public void addAction(TwitchBotAction action) {
		actionQueue.add(action);
	}

	public void startUpMessage() {
		actionQueue.add(TwitchBotAction.builder().action(EBotAction.Message.name())
				.value("Hi Leute, mein Name ist: " + config.getName()).build());
	}

	private void startActionListener() {
		Logger.debug("starting action listener for: " + config.info());
		actionListener.execute(() -> {
			while (true) {
				try {
					Logger.debug("Waiting for action: " + config.info());
					TwitchBotAction action = actionQueue.take();
					ActionHandler.handle(action, bot);
				} catch (Exception e) {
					Logger.error("Failed to take action from queue, exiting...");
					System.exit(0);
				}
			}
		});
	}
}
