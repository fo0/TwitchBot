package com.fo0.twitchbot.main;

import com.fo0.twitchbot.controller.Controller;

public class Main {

	/**
	* * == TODO: ==
	* * automatic answer questions
	* * idle detect of user (do defined actions)
	* * zu oft die selbe frage gestellt -> action
	* * save twitchbots sessions to load config/json
	* * version update functionality (updater)
	* * private message to join user with welcome message <br>
	* * private message to join user with help commands <br>
	* * receive private messages
	* * answer via the same channel or chat which the caller used
	* * Spam detection :: first parameter based detection finished <br>
	* * Custom Commands :: added via templates <br>
	* * REST Api <br>
	* * Web GUI <br>
	* * GoogleTranslate :: kostenpflichtig <br>
	* * BotAction Api / Templates :: first iteration finished  <br>
	* * gewinnspiel (giveaway) module
	* * music controller (!music vote youtube.link)
	* 
	* 
	*  == finished ==
	* * multiple bot creation
	* * custom action templates
	* * simple spam detectionw
	* * commandline api
	* * start params (options)
	* * 
	* @param args
	*/
	public static void main(String[] args) {
		Controller.bootstrap(args);
	}

}
