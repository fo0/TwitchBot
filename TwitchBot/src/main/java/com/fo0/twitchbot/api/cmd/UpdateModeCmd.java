package com.fo0.twitchbot.api.cmd;

import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.Main;
import com.fo0.twitchbot.controller.Controller;
import com.fo0.twitchbot.update.GitHubReleaseInfo;
import com.fo0.twitchbot.update.UpdateUtils;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.Utils;
import com.fo0.twitchbot.utils.commandline.CommandLineActions.ECommandLineMode;

public class UpdateModeCmd {

    private Consumer<String> printListener;
    private Consumer<ECommandLineMode> modeChanger;

    private boolean init = false;

    private boolean checkedForUpdate = false;
    private boolean available = false;

    private boolean askForUpdate;

    public UpdateModeCmd(Consumer<String> printListener, Consumer<ECommandLineMode> modeChanger) {
        this.printListener = printListener;
        this.modeChanger = modeChanger;
    }

    public void actions(String input) {
        if (!init) {
        }

        printListener.accept("checking for new updates...");

        if (!checkedForUpdate) {
            checkedForUpdate = true;
            available = UpdateUtils.isAvailable();
        }

        if (available) {
            askForUpdate = true;
            printListener.accept("new update found!");
            GitHubReleaseInfo info = UpdateUtils.getInfo();
            printListener.accept("Version: " + info.getVersion());
            printListener.accept("Release notes: " + info.getMessage());
            printListener.accept("starting update ? (y/n)");
            return;
        } else {
            askForUpdate = true;
            printListener.accept("no update available, switching to default mode");
            // switching to default mode
            modeChanger.accept(ECommandLineMode.Default);
        }

        if (!askForUpdate) {
            askForUpdate = true;
            if (StringUtils.equalsIgnoreCase(input, "y")) {
                Logger.info("performing update");
                printListener.accept("this feature is currently disabled");
                // TODO: Enable
                // UpdateUtils.doUpdate();
                // Utils.restartApplication(Main.class, Controller.arg);
                return;
            } else {
                Logger.info("skipping update");
                modeChanger.accept(ECommandLineMode.Default);
                return;
            }
        }

        return;
    }
}
