package com.fo0.twitchbot.main;

import com.fo0.twitchbot.controller.Controller;

public class Main {

	/**
	 * TODO: <br> 
	 * * private message to join user with welcome message <br>
	 * * private message to join user with help commands <br>
	 * * receive private messages
	 * * answer via the same channel or chat which the caller used
	 * * Spam detection :: first parameter based detection finished <br>
	 * * Custom Commands :: added via templates <br>
	 * * REST Api <br>
	 * * Web GUI <br>
	 * * GoogleTranslate <br>
	 * * BotAction Api / Templates :: first iteration finished  <br>
	 * * version update functionality (updater)
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Controller.bootstrap(args);
	}

}
