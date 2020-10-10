package com.fo0.twitchbot.api.auth;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.utils.CONSTANTS;

public class RESTApiRequestHandler {

	public static boolean validateApiKey(String apiKey) {
		if (CONSTANTS.REST_API_KEY == null) {
			// valid because no key set
			return true;
		}

		if (StringUtils.equals(CONSTANTS.REST_API_KEY, apiKey)) {
			return true;
		}

		return false;
	}

}
