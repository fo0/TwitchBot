//package com.fo0.twitchbot.api.rest;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.HeaderParam;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.core.Response;
//
//import com.fo0.twitchbot.api.auth.RESTApiRequestHandler;
//import com.fo0.twitchbot.api.rest.utils.RESTResponse;
//import com.fo0.twitchbot.utils.CONSTANTS;
//
//@Path(CONSTANTS.REST_PATH_TEMPLATE)
//public class ActionTemplateApi {
//
//	@POST
//	@Path(CONSTANTS.REST_PATH_TEMPLATE_ADD)
//	public Response add(@HeaderParam(value = "api_key") String apiKey) {
//		if (!RESTApiRequestHandler.validateApiKey(apiKey)) {
//			return RESTResponse.API_KEY_INVALID().build();
//		}
//
//		return RESTResponse.OK().build();
//	}
//
//	@GET
//	@Path(CONSTANTS.REST_PATH_TEMPLATE_LIST)
//	public Response list(@HeaderParam(value = "api_key") String apiKey) {
//		if (!RESTApiRequestHandler.validateApiKey(apiKey)) {
//			return RESTResponse.API_KEY_INVALID().build();
//		}
//
//		return RESTResponse.OK().build();
//	}
//
//	@GET
//	@Path(CONSTANTS.REST_PATH_TEMPLATE_FINDBYID)
//	public Response findbyid(@HeaderParam(value = "api_key") String apiKey) {
//		if (!RESTApiRequestHandler.validateApiKey(apiKey)) {
//			return RESTResponse.API_KEY_INVALID().build();
//		}
//
//		return RESTResponse.OK().build();
//	}
//
//}
