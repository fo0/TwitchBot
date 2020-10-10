package com.fo0.twitchbot.api.cmd;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.commandline.CommandLineActions;
import com.fo0.twitchbot.utils.commandline.CommandLineReader;

@Component
public class CommandlineApi {

    private CommandLineReader reader = null;

    @PostConstruct
    public void init() {
        start();
    }

    public void start() {
        Logger.info("Start the CommandLineApi");

        if (reader == null) {
            reader = new CommandLineReader(new CommandLineActions(reader));
        }

        reader.start();
    }

    public void stop() {
        Logger.info("Stop the CommandLineApi");
        if (reader != null)
            reader.stop();

        reader = null;
    }

}
