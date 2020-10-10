package com.fo0.twitchbot.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fo0.twitchbot.api.cmd.CommandlineApi;
import com.fo0.twitchbot.config.cmd.Config;
import com.fo0.twitchbot.config.cmd.Configuration;
import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.startup.StartUpMessage;
import com.fo0.twitchbot.utils.AuthFromFile;
import com.fo0.twitchbot.utils.Logger;

@Service
public class Controller {

    public static Config config = null;
    public static CommandlineApi cmdApi = null;

    @Autowired
    private StartUpMessage startUp;

    @Autowired
    private Configuration configuration;

    @PostConstruct
    public void init() {
        Logger.debug("starting controller");

        startUp.init();

        configuration.init();

        modules();

        // addDefaults();
    }

    public void preConstruct() {
        // disable ipv6
        System.setProperty("java.net.preferIPv4Stack", "true");
    }

    public void modules() {
        ControllerSystem.init();
        ControllerRest.init();
        ControllerTwitchBot.init();
    }

    public void enableCommandlineApi() {
        Logger.info("Starting CommandLineApi");
        cmdApi = new CommandlineApi();
    }

    public void addDefaults() {
        ControllerTwitchBot.addBot(TwitchBotConfig.builder()
                                                  .id("fo0mebot")
                                                  .name("fo0mebot")
                                                  .oauth(AuthFromFile.getTwitchOauthKey())
                                                  .channel("fo0me")
                                                  .build());
    }
}
