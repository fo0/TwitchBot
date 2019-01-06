package com.fo0.twitchbot.config;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

public class Config extends OptionsBase {

	@Option(name = "nogui", abbrev = 'n', category = "GUI", help = "Graphical User Interface", defaultValue = "false")
	public boolean nogui;
	
	@Option(name = "debug", abbrev = 'v', category = "DEBUG", help = "Debugging", defaultValue = "false")
	public boolean debug;

}
