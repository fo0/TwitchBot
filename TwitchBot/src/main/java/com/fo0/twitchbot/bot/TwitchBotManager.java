package com.fo0.twitchbot.bot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.bot.handler.ActionHandler;
import com.fo0.twitchbot.bot.handler.ChatActionHandler;
import com.fo0.twitchbot.bot.pool.BotActionPool;
import com.fo0.twitchbot.bot.pool.FAQStore;
import com.fo0.twitchbot.bot.template.FAQStoreTemplate;
import com.fo0.twitchbot.enums.EBotAction;
import com.fo0.twitchbot.model.TwitchBotAction;
import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.modules.SpamDetection;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.Utils;
import com.google.common.collect.Queues;

import lombok.Getter;

public class TwitchBotManager {

	@Getter
	private TwitchBotConfig config;
	private TwitchBot bot;

	private ExecutorService actionListener = Executors.newSingleThreadExecutor();

	private LinkedBlockingQueue<TwitchBotAction> actionQueue = Queues.newLinkedBlockingQueue();
	private SpamDetection spamDetector;
	private FAQStore autoFAQ;

	public TwitchBotManager(TwitchBotConfig config) {
		this.config = config;

		init();

		if (config.isAutostart()) {
			start();
		}
	}

	private void init() {
		bot = new TwitchBot(config.getName(), config.getChannel(), config.getOauth());
	}

	public void start() {
		Logger.debug("Starting twitchbot: " + config.info());

		applyConfigs();

		bot.connectToTwitch();

		addCapabilities();

		startActionListener();

		Utils.sleep(TimeUnit.SECONDS, 1);

		bot.addDefaultChannel();

		if (config.isStartUpMessage())
			startUpMessage();
	}

	private void applyConfigs() {
		if (config.isAllowChatCommands()) {
			addChatListener();
			
			if (config.isUserJoinMessage()) {
				addUserJoinListener();
			}

			if (config.isSpamDetection()) {
				addChatSpamDetection();
			}

			if (config.isAutoFAQ()) {
				addFAQModule();
			}
		}
	}

	public void stop() {
		bot.leaveChannel();
		actionListener.shutdownNow();
		actionQueue.clear();
	}

	private void addCapabilities() {
		Logger.debug("adding capabilities to twitchbot: " + config.info());
		bot.addCapabilities();
	}

	/**
	 * case insensitive input!
	 */
	private void addFAQModule() {
		Logger.debug("starting FAQ module for: " + config.info());
		autoFAQ = new FAQStore(FAQStoreTemplate.getCONFIG().getList(), e -> {
			addAction(TwitchBotAction.builder().value(e).build());
		});
	}

	private void addChatListener() {
		Logger.debug("starting chat listener for: " + config.info());
		bot.addChatListener(e -> {
			ChatActionHandler.handle(e, this);

			if (spamDetector != null) {
				spamDetector.addUser(e.getName());
			}

			if (autoFAQ != null) {
				autoFAQ.askQuestion(e.getMessage());
			}

		});
	}

	public void addChatSpamDetection() {
		Logger.debug("starting spam detector for: " + config.info());
		spamDetector = new SpamDetection(config.getSpamDetectionInterval(), config.getSpamDetectionThreshold(), e -> {
			String info = "detected spammers: " + StringUtils.join(e.keySet(), ", ");
			Logger.info(info);
			addAction(TwitchBotAction.builder().value(info).build());
		});
	}

	private void addUserJoinListener() {
		Logger.debug("starting user join listener for: " + config.info());
		bot.addUserJoinListener(e -> {
			addAction(TwitchBotAction.builder().value("User joined Channel: " + e).build());
		});
	}

	public void addAction(TwitchBotAction action) {
		if (action == null || action.getAction() == null) {
			return;
		}

		Logger.trace("Adding action to bot: " + action.getAction());
		actionQueue.add(action);
	}

	public void startUpMessage() {
		Utils.sleep(TimeUnit.SECONDS, 1);

		actionQueue.add(TwitchBotAction.builder().action(EBotAction.ChatMessage.name())
				.value("Hi Leute, mein Name ist: " + config.getName()).build());

		// wait, because twitch spam
		Utils.sleep(TimeUnit.SECONDS, 1);

		actionQueue.add(TwitchBotAction.builder().action(EBotAction.ChatMessage.name())
				.value("Meine Actions sind: " + StringUtils.join(
						BotActionPool.ACTIONS.keySet().parallelStream().map(e -> "!" + e).collect(Collectors.toList()),
						", "))
				.build());
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
