package com.fo0.twitchbot.api.cmd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fo0.twitchbot.controller.ControllerTwitchBot;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.commandline.CommandLineActions;
import com.fo0.twitchbot.utils.commandline.CommandLineReader;

@Component
public class CommandlineApi {

    @Autowired
    private ControllerTwitchBot bot;

    private CommandLineReader reader;

    public void start() {
        Logger.info("Start the CommandLineApi");

        if (reader == null) {
            reader = new CommandLineReader(new CommandLineActions(reader, bot));
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
