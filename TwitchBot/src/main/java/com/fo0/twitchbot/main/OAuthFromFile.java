package com.fo0.twitchbot.main;

import java.io.File;
import java.nio.charset.Charset;

import com.google.common.io.Files;

public class OAuthFromFile {

	public static String getKey() {
		try {
			return Files.toString(new File("auth/oauth.key"), Charset.defaultCharset());
		} catch (Exception e) {
		}

		return "no file or key found";
	}

}
