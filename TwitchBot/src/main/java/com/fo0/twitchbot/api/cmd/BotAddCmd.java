package com.fo0.twitchbot.api.cmd;

import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.config.twitchbots.TwitchBotConfigLoader;
import com.fo0.twitchbot.controller.ControllerTwitchBot;
import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.utils.AuthFromFile;
import com.fo0.twitchbot.utils.commandline.CommandLineActions.ECommandLineMode;

public class BotAddCmd {

	private Consumer<String> printListener;
	private Consumer<ECommandLineMode> modeChanger;

	private boolean init = false;

	private TwitchBotConfig config = TwitchBotConfig.builder().build();

	private boolean askForImportOAuthFromFile = false;

	public BotAddCmd(Consumer<String> printListener, Consumer<ECommandLineMode> modeChanger) {
		this.printListener = printListener;
		this.modeChanger = modeChanger;
	}

	public void actions(String input) {
		if (!init) {
			init = true;
			printListener.accept("Enter Name: ");
			return;
		}

		if (config.getName() == null) {
			config.setName(input);
			printListener.accept("Enter channel: ");
			return;
		}

		if (config.getChannel() == null) {
			config.setChannel(input);
			printListener.accept("Import OAuth From File ? (y/n) ");
			return;
		}
		if (!askForImportOAuthFromFile) {
			askForImportOAuthFromFile = true;
			if (StringUtils.equalsIgnoreCase(input, "y")) {
				config.setOauth(AuthFromFile.getTwitchOauthKey());
			}

			printListener.accept("Save (y/n): ");
			return;
		}

		if (StringUtils.equalsIgnoreCase(input, "y")) {
			// save
			TwitchBotConfigLoader.addBot(config);
			ControllerTwitchBot.addBot(config);
			modeChanger.accept(ECommandLineMode.Default);
			return;
		} else {
			modeChanger.accept(ECommandLineMode.Default);
			return;
		}

	}
}
