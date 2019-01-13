package com.fo0.twitchbot.utils.commandline;

import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.bot.EBotAction;
import com.fo0.twitchbot.config.ConfigParser;
import com.fo0.twitchbot.controller.Controller;
import com.fo0.twitchbot.controller.ControllerTwitchBot;
import com.fo0.twitchbot.model.TwitchBotAction;
import com.fo0.twitchbot.update.GitHubReleaseInfo;
import com.fo0.twitchbot.update.UpdateUtils;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.Utils;
import com.google.common.collect.Lists;

public class CommandLineActions implements Consumer<String> {

	private static final List<String> COMMANDS = Lists.newArrayList(
	//@formatter:off
			"help",
			"reload-templates",
			"commandline-api-restart", "commandline-api-stop", 
			"default-mode", "privatemessage-mode", "update-mode"
	//@formatter:on

	);

	private CommandLineReader reader = null;

	private String privateMessage_target_username;
	private boolean updateAvailable = false;

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

		switch (mode) {
		case PrivateMessage:
			privateMessageMode(t);
			return;

		case Update:

			break;

		default:
			// Default mode
			break;
		}

		String toLowerCaseAction = new String(t);
		switch (toLowerCaseAction) {
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

		case "default-mode":
			print("Switch to Mode: Default");
			mode = ECommandLineMode.Default;
			break;

		case "privatemessage-mode":
			// activate private message mode
			print("Switch to Mode: PrivateMessage");
			mode = ECommandLineMode.PrivateMessage;
			privateMessageMode(t);
			break;

		case "update-mode":
			print("Switch to Mode: Update");
			mode = ECommandLineMode.Update;
			updateMode(t);
			break;

		default:
			Logger.trace("Command not found: " + t);
			break;
		}
	}

	public void print(String msg) {
		System.out.println(Utils.getCurrentTime() + " [Console|" + mode + "] " + msg);
	}

	private void updateMode(String t) {
		if (StringUtils.equalsIgnoreCase(t, "Exit")) {
			print("Switch to Mode: Default");
			mode = ECommandLineMode.Default;
			return;
		}

		if (updateAvailable) {
			if (StringUtils.equalsIgnoreCase(t, "y")) {
				Logger.info("performing update");
			} else {
				Logger.info("skipping update");
			}
		} else {
			print("checking for new updates...");
			boolean available = UpdateUtils.isAvailable();
			available = true;
			if (available) {
				print("new update found!");
				GitHubReleaseInfo info = UpdateUtils.getInfo();
				print("Version: " + info.getVersion());
				print("Release notes: " + info.getMessage());
				print("starting update ? (y/n)");
				return;
			} else {
				print("no update available, switching to default mode");
				// switching to default mode
				mode = ECommandLineMode.Default;
				updateAvailable = false;
			}
		}

		return;
	}

	private void privateMessageMode(String t) {
		print("Enter target User: ");

		if (StringUtils.equalsIgnoreCase(t, "Exit")) {
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
			print("You can stop the mode and change back to mode: default with the input \"Exit\"");
			return;
		}

		// TODO: access bot and send message
		System.out.println("### SENDING MESSAGE TO USER: " + privateMessage_target_username + " --> " + t);
		ControllerTwitchBot.getBot("fo0mebot").addAction(TwitchBotAction.builder()
				.toUser(privateMessage_target_username).action(EBotAction.PrivateMessage.name()).value(t).build());
		return;
	}

	public enum ECommandLineMode {
		Default, PrivateMessage, Update
	}
}
