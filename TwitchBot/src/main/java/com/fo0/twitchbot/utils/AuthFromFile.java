package com.fo0.twitchbot.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import com.fo0.twitchbot.model.TwitchBotConfig;
import com.google.common.io.Files;

public class AuthFromFile {

	private static final String TWITCH_API_DOC = "https://dev.twitch.tv/docs/irc/";
	private static final String TWITCH_OAUTH_GENERATOR = "https://twitchapps.com/tmi/";
	private static final String FILE_PATH_TWITCH_OAUTH_KEY = CONSTANTS.CONFIG_FOLDER_PATH + "/auth/twitch_oauth.key";

	private static final String GOOGLE_API_DOC = "https://cloud.google.com/docs/authentication/api-keys";
	private static final String GOOGLE_API_GENERATOR = "https://console.cloud.google.com/apis";
	private static final String FILE_PATH_GOOGLE_TRANSLATOR_KEY = CONSTANTS.CONFIG_FOLDER_PATH + "/auth/google_translator.key";

	public static String getTwitchOauthKey() {
		if (!Paths.get(FILE_PATH_TWITCH_OAUTH_KEY).toFile().exists()) {
			Logger.info("could not find twitch oauth file at path: "
					+ Paths.get(FILE_PATH_TWITCH_OAUTH_KEY).toAbsolutePath());
			Logger.info("start creating an empty file: " + Utils.createNewFileWithDirectories(FILE_PATH_TWITCH_OAUTH_KEY));
			try {
				Paths.get(FILE_PATH_TWITCH_OAUTH_KEY).toFile().mkdirs();
				Paths.get(FILE_PATH_TWITCH_OAUTH_KEY).toFile().createNewFile();
			} catch (IOException e) {
			}
			Logger.info("please add your oauth key to the file, guide at: " + TWITCH_API_DOC);
			Logger.info("in short: Use this Site to generate your oauth key: " + TWITCH_OAUTH_GENERATOR);
			Logger.info("after generating the key, paste it to the file and restart the application");
			Logger.info("exiting now...");
			System.exit(0);
		}

		try {
			return Files.toString(new File(FILE_PATH_TWITCH_OAUTH_KEY), Charset.defaultCharset());
		} catch (Exception e) {
			Logger.error("could not read file with oauth key, exiting...");
		}

		return "no file or key found";
	}

	public static void addTwitchOAuthKeyToConfig(TwitchBotConfig cfg) {
		Logger.warn(String.format("[DEV] Automatically adding Twitch Key to Bot %s [%s]", cfg.getName(), cfg.getId()));
		cfg.setOauth(getTwitchOauthKey());
	}

	public static String getGoogleTranslatorKey() {
		if (!Paths.get(FILE_PATH_GOOGLE_TRANSLATOR_KEY).toFile().exists()) {
			Logger.info("could not find google translator key file at path: "
					+ Paths.get(FILE_PATH_GOOGLE_TRANSLATOR_KEY).toAbsolutePath());
			Logger.info("start creating an empty file: " + Utils.createNewFileWithDirectories(FILE_PATH_GOOGLE_TRANSLATOR_KEY));
			try {
				Paths.get(FILE_PATH_GOOGLE_TRANSLATOR_KEY).toFile().mkdirs();
				Paths.get(FILE_PATH_GOOGLE_TRANSLATOR_KEY).toFile().createNewFile();
			} catch (IOException e) {
			}
			Logger.info("please add your google translator key to the file, guide at: " + GOOGLE_API_DOC);
			Logger.info("in short: Use this Site to generate your oauth key: " + GOOGLE_API_GENERATOR);
			Logger.info("after generating the key, paste it to the file and restart the application");
			Logger.info("exiting now...");
			System.exit(0);
		}

		try {
			return Files.toString(new File(FILE_PATH_GOOGLE_TRANSLATOR_KEY), Charset.defaultCharset());
		} catch (Exception e) {
			Logger.error("could not read file with oauth key, exiting...");
		}

		return "no file or key found";
	}

}
