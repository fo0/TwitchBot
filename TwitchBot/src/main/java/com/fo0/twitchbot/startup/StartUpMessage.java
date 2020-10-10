package com.fo0.twitchbot.startup;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fo0.twitchbot.spring.components.AppArgs;
import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.Logger;

@Component
public class StartUpMessage {

    @Autowired
    private AppArgs args;
    
	public void init() {
	    System.out.println();
		Logger.info("######################################################");
		Logger.info("    Starting TwitchBot");
		Logger.info("    Version: " + CONSTANTS.VERSION);
		Logger.info("    Author: fo0");
		Logger.info("    GitHub: https://github.com/fo0/TwitchBot.git");
		Logger.info("    Options: " + StringUtils.join(args.getCmdArgs(), ", "));
		Logger.info("######################################################");
		System.out.println();
	}
	
}
