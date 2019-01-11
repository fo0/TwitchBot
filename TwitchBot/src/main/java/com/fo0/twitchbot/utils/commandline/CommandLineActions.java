package com.fo0.twitchbot.utils.commandline;

import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.config.ConfigParser;
import com.fo0.twitchbot.controller.Controller;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.Utils;
import com.google.common.collect.Lists;

public class CommandLineActions implements Consumer<String> {

	private static final List<String> COMMANDS = Lists.newArrayList("help", "reload-templates",
			"commandline-api-restart", "commandline-api-stop");

	@Override
	public void accept(String t) {
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

		default:
			Logger.debug("Command not found: " + t);
			break;
		}
	}

	public static void print(String msg) {
		System.out.println(Utils.getCurrentTime() + " [Console-Api] " + msg);
	}

}
