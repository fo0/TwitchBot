package com.fo0.twitchbot.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.fo0.twitchbot.api.auth.RESTApiRequestHandler;
import com.fo0.twitchbot.api.rest.utils.RESTResponse;
import com.fo0.twitchbot.controller.ControllerSystem;
import com.fo0.twitchbot.utils.CONSTANTS;

@Path(CONSTANTS.REST_PATH_SYSTEM)
public class SystemApi {

	@GET
	@Path(CONSTANTS.REST_PATH_SYSTEM_INFO)
	public Response info(@HeaderParam(value = "api_key") String apiKey) {
		if (!RESTApiRequestHandler.validateApiKey(apiKey)) {
			return RESTResponse.API_KEY_INVALID().build();
		}

		return RESTResponse.OK().entity(ControllerSystem.info()).build();
	}

}
