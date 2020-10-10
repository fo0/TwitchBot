package com.fo0.twitchbot.utils;

import java.net.URI;
import java.util.Set;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import lombok.Getter;

public class RESTServer {

	@Getter
	private HttpServer server = null;

	private ResourceConfig rc = new ResourceConfig();

	public RESTServer(int port, Set<Class<?>> restClasses, String restPackage) {
		rc.register(MultiPartFeature.class);

		rc.registerClasses(restClasses);
		rc.packages(restPackage);

		try {
			//@formatter:on
			Logger.debug("REST Server on Address: " + ("http://" + CONSTANTS.REST_ADDRESS + ":" + port));
			Logger.debug("REST Server on Path: " + CONSTANTS.REST_ROOT_PATH);
			Logger.debug("REST Server Api Key: " + (CONSTANTS.REST_API_KEY == null ? "unset" : CONSTANTS.REST_API_KEY));
			server = GrizzlyHttpServerFactory.createHttpServer(new URI("http://" + CONSTANTS.REST_ADDRESS + ":" + port), rc);
			//@formatter:off
		} catch (Exception e) {
			Logger.error("failed to create rest server" + e);
		}
	}

}
