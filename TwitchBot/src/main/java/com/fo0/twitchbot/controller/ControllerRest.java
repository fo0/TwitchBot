package com.fo0.twitchbot.controller;

import java.io.IOException;

import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.RESTServer;

public class ControllerRest {

	private static RESTServer server = null;

	public static void init() {
		Logger.info("starting controller: rest");
		server = new RESTServer(CONSTANTS.REST_PORT, CONSTANTS.REST_CLASSES, CONSTANTS.REST_PACKAGE);
	}

	public static void start() {
		try {
			server.getServer().start();
		} catch (IOException e) {
			Logger.error("failed to start rest server: " + e.getMessage());
		}
	}

	public static void stop() {
		server.getServer().stop();
	}

	public static RESTServer get() {
		return server;
	}
}
