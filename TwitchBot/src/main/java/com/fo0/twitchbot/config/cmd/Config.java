package com.fo0.twitchbot.config.cmd;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

public class Config extends OptionsBase {

	@Option(name = "nogui", abbrev = 'n', category = "GUI", help = "Graphical User Interface", defaultValue = "false")
	public boolean nogui;

	@Option(name = "debug", abbrev = 'v', category = "DEBUG", help = "Debugging", defaultValue = "false")
	public boolean debug;

	@Option(name = "config", abbrev = 'c', category = "Config", help = "Config-Folder", defaultValue = "config")
	public String configDir;

	@Option(name = "commandLineApi", abbrev = 'a', category = "Api", help = "Commandline Api", defaultValue = "false")
	public boolean commandLineApi;

	@Option(name = "apiKey", abbrev = 'k', category = "REST", help = "REST ApiKey", defaultValue = "")
	public String apiKey;

	@Option(name = "port", abbrev = 'p', category = "REST", help = "REST Api Port", defaultValue = "1234")
	public int port;
}
