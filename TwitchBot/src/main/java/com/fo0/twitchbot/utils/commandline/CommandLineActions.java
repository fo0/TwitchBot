package com.fo0.twitchbot.utils.commandline;

import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.api.cmd.BotAddCmd;
import com.fo0.twitchbot.api.cmd.UpdateModeCmd;
import com.fo0.twitchbot.bot.EBotAction;
import com.fo0.twitchbot.config.cmd.ConfigParser;
import com.fo0.twitchbot.controller.Controller;
import com.fo0.twitchbot.controller.ControllerTwitchBot;
import com.fo0.twitchbot.model.TwitchBotAction;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.Utils;
import com.google.common.collect.Lists;

public class CommandLineActions implements Consumer<String> {

	private static final List<String> COMMANDS = Lists.newArrayList(
	//@formatter:off
			"help",
			"reload-templates",
			"commandline-api-restart", "commandline-api-stop", 
			"default-mode", "privatemessage-mode", "update-mode","bot-add-mode"
	//@formatter:on

	);

	private CommandLineReader reader = null;

	private String privateMessage_bot_name;
	private String privateMessage_target_username;
	private boolean updateCheck = false;

	private ECommandLineMode mode = ECommandLineMode.Default;

	private BotAddCmd botAdd = null;

	private Consumer<String> printListener = e -> print(e);
	private Consumer<ECommandLineMode> modeChanger = e -> setMode(e);

	private UpdateModeCmd update;

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
			updateMode(t);
			break;

		case BotAdd:
			botadd(t);
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
			print("Enter a Bot Name: ");
			break;

		case "update-mode":
			print("Switch to Mode: Update");
			mode = ECommandLineMode.Update;
			update = new UpdateModeCmd(printListener, modeChanger);
			updateMode(t);
			break;

		case "bot-add-mode":
			print("Switch to Mode: Bot-Add");
			mode = ECommandLineMode.BotAdd;
			botAdd = new BotAddCmd(printListener, modeChanger);
			botadd(t);
			break;

		default:
			Logger.trace("Command not found: " + t);
			break;
		}
	}

	public void print(String msg) {
		System.out.println(Utils.getCurrentTime() + " [Console|" + mode + "] " + msg);
	}

	public void setMode(ECommandLineMode mode) {
		print("Set Mode To: " + mode);
		this.mode = mode;
	}

	private void botadd(String t) {
		if (StringUtils.equalsIgnoreCase(t, "Exit")) {
			print("Switch to Mode: Default");
			mode = ECommandLineMode.Default;
			return;
		}

		botAdd.actions(t);
		return;
	}

	private void updateMode(String t) {
		if (StringUtils.equalsIgnoreCase(t, "Exit")) {
			print("Switch to Mode: Default");
			mode = ECommandLineMode.Default;
			return;
		}

		update.actions(t);
		return;
	}

	private void privateMessageMode(String t) {
		if (StringUtils.equalsIgnoreCase(t, "Exit")) {
			print("Switch to Mode: Default");
			mode = ECommandLineMode.Default;
			privateMessage_target_username = null;
			privateMessage_bot_name = null;
			return;
		}

		if (StringUtils.isBlank(privateMessage_bot_name)) {
			if (ControllerTwitchBot.getBotByName(t) == null) {
				print("Could not find a registered Bot with name: " + t);
				print("Enter another Bot Name: ");
				return;
			}

			privateMessage_bot_name = t;
			print("Enter target User: ");
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

//		System.out.println("### SENDING MESSAGE TO USER: " + privateMessage_target_username + " --> " + t);
		ControllerTwitchBot.getBotByName(privateMessage_bot_name).addAction(TwitchBotAction.builder()
				.toUser(privateMessage_target_username).action(EBotAction.PrivateMessage.name()).value(t).build());
		return;
	}

	public enum ECommandLineMode {
		Default, PrivateMessage, Update, BotAdd
	}
}
