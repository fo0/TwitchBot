package com.fo0.twitchbot.config.cmd;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fo0.twitchbot.bot.template.ActionDefaultTemplate;
import com.fo0.twitchbot.bot.template.FAQStoreTemplate;
import com.fo0.twitchbot.controller.Controller;
import com.fo0.twitchbot.spring.components.AppArgs;
import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.Logger;

@Component
public class Configuration {

    @Autowired
    private AppArgs args;

    @Autowired
    private Controller controller;
    
    @PostConstruct
    public void init() {
        Logger.debug("starting configuration");

        Controller.config = ConfigParser.parseConfig(args.getCmdArgs());

        applyConfig();

        ActionDefaultTemplate.init();

        FAQStoreTemplate.init();
    }

    /**
     * 
     * @Created 10.10.2020 - 22:48:03
     * @author fo0 (GH:fo0)
     */
    private void applyConfig() {
        CONSTANTS.DEBUG = Controller.config.debug;

        if (StringUtils.isNotBlank(Controller.config.configDir)) {
            // applying botcnfig
            CONSTANTS.CONFIG_FOLDER_PATH = Controller.config.configDir;
        }

        if (Controller.config.commandLineApi) {
            controller.enableCommandlineApi();
        }

        if (Controller.config.port != 1234) {
            CONSTANTS.REST_PORT = Controller.config.port;
        }

        if (StringUtils.isNotBlank(Controller.config.apiKey)) {
            CONSTANTS.REST_API_KEY = Controller.config.apiKey;
        }
    }

}
