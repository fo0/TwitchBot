package com.fo0.twitchbot.config.cmd;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fo0.twitchbot.api.cmd.CommandlineApi;
import com.fo0.twitchbot.bot.template.ActionDefaultTemplate;
import com.fo0.twitchbot.bot.template.FAQStoreTemplate;
import com.fo0.twitchbot.spring.components.AppArgs;
import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.Logger;

@Component
public class Configuration {

    @Autowired
    private AppArgs args;

    @Autowired
    private CommandlineApi cmdApi;

    @PostConstruct
    public void init() {
        Logger.debug("starting configuration");

        Config config = createConfigByParser();
        
        CONSTANTS.DEBUG = config.debug;

        if (StringUtils.isNotBlank(config.configDir)) {
            // applying botcnfig
            CONSTANTS.CONFIG_FOLDER_PATH = config.configDir;
        }

        if (config.commandLineApi) {
            cmdApi.start();
        }

        if (config.port != 1234) {
            CONSTANTS.REST_PORT = config.port;
        }

        if (StringUtils.isNotBlank(config.apiKey)) {
            CONSTANTS.REST_API_KEY = config.apiKey;
        }

        ActionDefaultTemplate.init();

        FAQStoreTemplate.init();
    }

    @Bean
    public Config createConfigByParser() {
        return ConfigParser.parseConfig(args.getCmdArgs());
    }

}
