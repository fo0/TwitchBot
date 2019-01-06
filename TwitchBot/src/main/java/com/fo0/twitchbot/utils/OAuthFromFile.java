package com.fo0.twitchbot.utils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import com.google.common.io.Files;

public class OAuthFromFile {

	private static final String FILE_PATH = "auth/oauth.key";

	public static String getKey() {
		if (!Paths.get(FILE_PATH).toFile().exists()) {
			Logger.info("could not find oauth file at path: " + Paths.get(FILE_PATH).toAbsolutePath());
			Logger.info("start creating an empty file: " + Paths.get(FILE_PATH).toAbsolutePath());
			Logger.info("please add your oauth key to the file, guide at: https://dev.twitch.tv/docs/irc/");
			Logger.info("in short: Use this Site to generate your oauth key: https://twitchapps.com/tmi/");
			Logger.info("after generating the key, paste it to the file and restart the application");
			Logger.info("exiting now...");
			System.exit(0);
		}

		try {
			return Files.toString(new File(FILE_PATH), Charset.defaultCharset());
		} catch (Exception e) {
			Logger.error("could not read file with oauth key, exiting...");
		}

		return "no file or key found";
	}

}
