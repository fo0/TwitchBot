package com.fo0.twitchbot.utils.httpclient;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.utils.Logger;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class HttpClient {

	private OkHttpClient.Builder client;
	private HttpClientConfig config;
	private Builder requestBuilder;
	private Request request;
	private Response response;

	public Response execute() {
		prepareRequest();

		// only if prepare is success, create client
		client = new OkHttpClient.Builder();
		// handle ssl if needed
		handleSSL();

		// build request
		buildRequest();

		if (printRequest() == null) {
			Logger.error("failed request is null");
			return null;
		}

		handleInterceptors();

		switch (config.getType()) {
		case GET:
			response = executeGet();
			break;

		case POST:
			response = executePost();
			break;

		default:
			Logger.warn("not supported type: " + config.getType() + " .Fallback to defaults (" + Type.GET + ")");
			executeGet();
			break;
		}

		printResponse();

		return response;
	}

	public String printRequest() {
		try {
			String output = "Empty, no request found";
			if (request != null) {
				output = request.toString();
			}
			Logger.debug("starting " + output);
			return output;
		} catch (Exception e) {
			Logger.error("failed to print request, something went wrong!" + e);
		}
		return null;
	}

	public String printResponse() {
		try {
			String output = "Empty, no response found";
			if (response != null) {
				output = response.toString();
			}
			Logger.debug("finished " + output);
			return output;
		} catch (Exception e) {
			Logger.error("failed to print response, something went wrong! " + e);
		}
		return null;
	}

	private void handleInterceptors() {
		if (config.getClientInterceptor() != null) {
			client = config.getClientInterceptor().apply(client);
		}
	}

	private Response executePost() {
		try {
			return executeHandler();
		} catch (Exception e) {
			Logger.error("failed to execute Post request " + e);
		}
		return null;
	}

	private Response executeGet() {
		try {
			return executeHandler();
		} catch (Exception e) {
			Logger.error("failed to execute get request" + e);
		}
		return null;
	}

	@SuppressWarnings({ "deprecation" })
	private Response executeHandler() throws Exception {
		try {
			configureTimeouts();

			return client.build().newCall(request).execute();
		} catch (Exception e) {
			throw e;
		} finally {
			if (config.isCloseRequest()) {
				if (response != null) {
					Logger.trace("closing response");
					IOUtils.closeQuietly(response);
				}
			}
		}
	}

	private void configureTimeouts() {
		if (config.getConnectionTimeout() > -1)
			client.connectTimeout(config.getConnectionTimeout(), TimeUnit.MILLISECONDS);

		if (config.getReadTimeout() > -1)
			client.readTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS);

		if (config.getCallTimeout() > -1) {
			client.callTimeout(config.getCallTimeout(), TimeUnit.MILLISECONDS);
		}
	}

	private void validateConfig() {
		Logger.trace("starting validation");

		if (config.isIgnoreValidation()) {
			Logger.warn("ignore validations, please take care!");
		} else {
			Objects.requireNonNull(config.getUrl(), "Missing Url");

			if (config.isAutoCorrect()) {
				// TODO: auto correction
				Logger.warn("ignore auto correction, currently not implemented");
			}

			if (config.getType() == null) {
				// add basic type: GET
				config.setType(Type.GET);
			}

			if (config.isTypeEqual(Type.POST) && config.getRequestBody() == null) {
				// detecting post request with empty body
				// TODO: throw exception or s.th.
			}
		}

		Logger.trace("finished validation");
	}

	private HttpClient buildRequest() {
		request = requestBuilder.build();
		return this;
	}

	private HttpClient prepareRequest() {
		// validate config before preapre
		validateConfig();

		requestBuilder = new Request.Builder().url(createHttpUrl(config)).headers(createHeaders(config));

		if (config.isTypeEqual(Type.POST))
			requestBuilder = requestBuilder.post(config.getRequestBody());

		return this;
	}

	private Headers createHeaders(HttpClientConfig builder) {
		return Headers.of(builder.getHeaders());
	}

	private HttpUrl createHttpUrl(HttpClientConfig builder) {
		HttpUrl.Builder urlBuilder = HttpUrl.parse(builder.getUrl()).newBuilder();
		builder.getParams().entrySet().parallelStream().forEach(param -> {
			urlBuilder.addQueryParameter(param.getKey(), param.getValue());
		});
		return urlBuilder.build();
	}

	public HttpClient(HttpClientConfig builder) {
		this.config = builder;
	}

	public HttpClient(String urlGet) {
		this.config = HttpClientConfig.builder().url(urlGet).build();
	}

	public static HttpClientConfig.HttpClientConfigBuilder builder() {
		return HttpClientConfig.builder();
	}

	private void handleSSL() {
		if (config.isIgnoreSSL() && isHttps(config.getUrl())) {
			Logger.trace("watchdog detect SSL address");
			// if it is private network - auto intercept
			client = HttpClientCertHandler.ignoreAllCerts(client);
		}

	}

	private boolean isHttps(String url) {
		return (StringUtils.isNotEmpty(url) && url.startsWith("https")) ? true : false;
	}

}
