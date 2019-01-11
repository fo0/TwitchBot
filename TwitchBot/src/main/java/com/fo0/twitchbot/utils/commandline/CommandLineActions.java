package com.fo0.twitchbot.utils.commandline;

import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.bot.EBotAction;
import com.fo0.twitchbot.config.ConfigParser;
import com.fo0.twitchbot.controller.Controller;
import com.fo0.twitchbot.controller.ControllerTwitchBot;
import com.fo0.twitchbot.model.TwitchBotAction;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.Utils;
import com.google.common.collect.Lists;

public class CommandLineActions implements Consumer<String> {

	private static final List<String> COMMANDS = Lists.newArrayList("help", "reload-templates",
			"commandline-api-restart", "commandline-api-stop", "default", "privatemessage");

	private CommandLineReader reader;

	private String privateMessage_target_username;

	private ECommandLineMode mode = ECommandLineMode.Default;

	public CommandLineActions(CommandLineReader reader) {
		this.reader = reader;
	}

	@Override
	public void accept(String t) {
		if (StringUtils.isBlank(t)) {
			Logger.trace("IGNORE: blank commandline api input");
			return;
		}

		if (mode == ECommandLineMode.PrivateMessage) {
			// detect mode change
			if (StringUtils.equals(t, ECommandLineMode.Default.name())) {
				print("Switch to Mode: Default");
				mode = ECommandLineMode.Default;
				privateMessage_target_username = null;
				return;
			}

			// handle private messages
			// add target user name
			if (StringUtils.isBlank(privateMessage_target_username)) {
				privateMessage_target_username = t;
				print("All Text below sends to user: " + privateMessage_target_username);
				print("You can stop the mode and change back to mode: default with the input \"Default\"");
				return;
			}

			// TODO: access bot and send message
			System.out.println("### SENDING MESSAGE TO USER: " + privateMessage_target_username + " --> " + t);
			ControllerTwitchBot.getBot("fo0mebot")
					.addAction(TwitchBotAction.builder().
							toUser(privateMessage_target_username)
							.action(EBotAction.PrivateMessage.name())
							.value(t)
							.build());
			return;
		}

		switch (t) {
		case "help":
			print("Current Commands are: " + StringUtils.join(COMMANDS, ", "));
			break;

		case "reload-templates":
			ConfigParser.reloadActionTemplate();
			break;

		case "commandline-api-restart":
			new Thread(() -> {
				Controller.cmdApi.stop();
				Utils.sleep(1000);
				Controller.cmdApi.start();
			}).start();
			break;

		case "commandline-api-stop":
			Controller.cmdApi.stop();
			break;

		case "default":
			print("Switch to Mode: Default");
			privateMessage_target_username = null;
			break;

		case "privatemessage":
			// activate private message mode
			print("Switch to Mode: PrivateMessage");
			mode = ECommandLineMode.PrivateMessage;
			print("Enter target User: ");
			break;

		default:
			Logger.trace("Command not found: " + t);
			break;
		}
	}

	public static void print(String msg) {
		System.out.println(Utils.getCurrentTime() + " [Console-Api] " + msg);
	}

	public enum ECommandLineMode {
		Default, PrivateMessage
	}
}
