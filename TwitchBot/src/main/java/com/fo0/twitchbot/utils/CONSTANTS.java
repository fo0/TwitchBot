package com.fo0.twitchbot.utils;

public class CONSTANTS {

	public static boolean DEBUG = true;
	public static boolean TRACE = false;

	public static final String VERSION = "0.0.2";

	public static final int ID_LENGTH = 30;
	public static final String GITHUB_URI = "fo0/TwitchBot";

	public static String CONFIG_FOLDER_PATH = "config";

	/**
	 * Matching score
	 */
	public static final double MATCHING_SCORE = 0.90;

	/**
	 * REST
	 */
	public static final String REST_ADDRESS = "0.0.0.0";
	public static String REST_API_KEY = null;
	public static int REST_PORT = 1234;
	public static final String REST_ROOT_PATH = "/api";

	/**
	 * TEMPLATE
	 */
	public static final String REST_PATH_TEMPLATE = REST_ROOT_PATH + "/actiontemplate";
	public static final String REST_PATH_TEMPLATE_ADD = "add";
	public static final String REST_PATH_TEMPLATE_FINDBYID = "findbyid";
	public static final String REST_PATH_TEMPLATE_LIST = "list";

	/**
	 * BOT
	 */
	public static final String REST_PATH_BOT = REST_ROOT_PATH + "/bot";
	public static final String REST_PATH_BOT_ADD = "add";
	public static final String REST_PATH_BOT_FINDBYID = "findbyid";
	public static final String REST_PATH_BOT_LIST = "list";

	/**
	 * SYSTEM
	 */
	public static final String REST_PATH_SYSTEM = REST_ROOT_PATH + "/system";
	public static final String REST_PATH_SYSTEM_INFO = "info";
	public static final String REST_PATH_SYSTEM_FINDBYID = "findbyid";
	public static final String REST_PATH_SYSTEM_LIST = "list";

}
