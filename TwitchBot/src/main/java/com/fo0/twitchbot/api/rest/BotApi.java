package com.fo0.twitchbot.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.api.auth.RESTApiRequestHandler;
import com.fo0.twitchbot.api.rest.utils.RESTResponse;
import com.fo0.twitchbot.bot.TwitchBotManager;
import com.fo0.twitchbot.controller.ControllerTwitchBot;
import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.utils.CONSTANTS;
import com.google.gson.Gson;

@Path(CONSTANTS.REST_PATH_BOT)
public class BotApi {

	@POST
	@Path(CONSTANTS.REST_PATH_BOT_ADD)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(@HeaderParam(value = "api_key") String apiKey, String twitchbotConfig) {
		if (!RESTApiRequestHandler.validateApiKey(apiKey)) {
			return RESTResponse.API_KEY_INVALID().build();
		}

		TwitchBotConfig tbc = new Gson().fromJson(twitchbotConfig, TwitchBotConfig.class);
		if (tbc == null) {
			return RESTResponse.BAD_REQUEST().build();
		}

		ControllerTwitchBot.addBot(tbc);

		return RESTResponse.OK().build();
	}

	@GET
	@Path(CONSTANTS.REST_PATH_BOT_LIST)
	public Response list(@HeaderParam(value = "api_key") String apiKey) {
		if (!RESTApiRequestHandler.validateApiKey(apiKey)) {
			return RESTResponse.API_KEY_INVALID().build();
		}

		return RESTResponse.OK().entity(ControllerTwitchBot.getSessionConfigs()).build();
	}

	@GET
	@Path(CONSTANTS.REST_PATH_BOT_FINDBYID)
	public Response findByID(@HeaderParam(value = "api_key") String apiKey, @QueryParam(value = "id") String id) {
		if (!RESTApiRequestHandler.validateApiKey(apiKey)) {
			return RESTResponse.API_KEY_INVALID().build();
		}

		if (StringUtils.isBlank(id)) {
			return RESTResponse.BAD_REQUEST().build();
		}

		TwitchBotManager tbc = ControllerTwitchBot.getBotByID(id);
		if (tbc == null) {
			return RESTResponse.NOT_FOUND().build();
		}

		return RESTResponse.OK().entity(tbc.getConfig()).build();
	}

}
