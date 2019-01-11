package com.fo0.twitchbot.api.cmd;

import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.commandline.CommandLineActions;
import com.fo0.twitchbot.utils.commandline.CommandLineReader;

public class CommandlineApi {

	private CommandLineReader reader = null;

	public CommandlineApi() {
		start();
	}

	private void init() {
		reader = new CommandLineReader(new CommandLineActions());
	}

	public void start() {
		Logger.info("Start the CommandLineApi");

		if (reader == null) {
			init();
		}

		reader.start();
	}

	public void stop() {
		Logger.info("Stop the CommandLineApi");
		if (reader != null)
			reader.stop();

		reader = null;
	}

}
