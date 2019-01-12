package com.fo0.twitchbot.modules;

import org.apache.commons.collections4.MapUtils;

import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.SpamDetection;

public class ChatSpamDetection {

	private SpamDetection detector = null;

	public ChatSpamDetection() {
		detector = new SpamDetection(2000, 1, e -> {
			Logger.info("found spammers: ");
			MapUtils.debugPrint(System.out, "list of spammers", e);
		});
	}

}
