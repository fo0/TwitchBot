package com.fo0.twitchbot.config.cmd;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

public class Config extends OptionsBase {

	@Option(name = "nogui", abbrev = 'n', category = "GUI", help = "Graphical User Interface", defaultValue = "false")
	public boolean nogui;

	@Option(name = "debug", abbrev = 'v', category = "DEBUG", help = "Debugging", defaultValue = "false")
	public boolean debug;

	@Option(name = "actiontemplate", abbrev = 'a', category = "Template", help = "Action", defaultValue = "")
	public String actionTemplate;

	@Option(name = "variabletemplate", abbrev = 'r', category = "Template", help = "Action", defaultValue = "")
	public String variableTemplate;

	@Option(name = "commandLineApi", abbrev = 'c', category = "Api", help = "Commandline Api", defaultValue = "false")
	public boolean commandLineApi;
}
