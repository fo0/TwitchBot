package com.fo0.twitchbot.main;

import com.fo0.twitchbot.controller.Controller;

public class Main {

	/**
	 * TODO: <br> 
	 * * private message to join user with welcome message <br>
	 * * private message to join user with help commands <br>
	 * * Spam detection <br>
	 * * Custom Commands :: added via templates <br>
	 * * REST Api <br>
	 * * Web GUI <br>
	 * * GoogleTranslate <br>
	 * * BotAction Api / Templates :: first iteration finished  <br>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Controller.bootstrap(args);
	}

}
