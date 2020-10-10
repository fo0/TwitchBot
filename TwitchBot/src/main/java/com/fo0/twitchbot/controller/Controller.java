package com.fo0.twitchbot.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fo0.twitchbot.api.cmd.CommandlineApi;
import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.startup.StartUpMessage;
import com.fo0.twitchbot.utils.AuthFromFile;
import com.fo0.twitchbot.utils.Logger;

@Service
public class Controller {

    @Autowired
    public CommandlineApi cmdApi;

    @Autowired
    private StartUpMessage startUp;

    @Autowired
    private ControllerTwitchBot bot;

    @PostConstruct
    public void init() {
        Logger.debug("starting controller");

        startUp.init();
    }

    public void preConstruct() {
        // disable ipv6
        System.setProperty("java.net.preferIPv4Stack", "true");
    }

    @PostConstruct
    public void addDefaults() {
        bot.addBot(TwitchBotConfig.builder()
                                  .id("fo0mebot")
                                  .name("fo0mebot")
                                  .oauth(AuthFromFile.getTwitchOauthKey())
                                  .channel("fo0me")
                                  .build());
    }
}
