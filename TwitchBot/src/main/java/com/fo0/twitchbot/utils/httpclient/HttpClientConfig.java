package com.fo0.twitchbot.utils.httpclient;

import java.util.Map;
import java.util.function.UnaryOperator;

import org.apache.commons.collections4.MapUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

@Builder
@Getter
@ToString(exclude = { "requestBody", "clientInterceptor" })
public class HttpClientConfig  {

	@Setter
	private Type type;

	@Setter
	private String url;

	@Builder.Default
	private boolean ignoreSSL = false;
	@Builder.Default
	private boolean ignoreValidation = false;
	@Builder.Default
	private boolean closeRequest = false;
	@Builder.Default
	private boolean autoCorrect = true;

	@Singular
	private Map<String, String> headers;
	@Singular
	private Map<String, String> params;

	@Builder.Default
	private long connectionTimeout = -1;
	@Builder.Default
	private long readTimeout = -1;
	@Builder.Default
	private long callTimeout = -1;

	private RequestBody requestBody;

	private UnaryOperator<OkHttpClient.Builder> clientInterceptor;

	public boolean isTypeEqual(Type type) {
		if(type == null)
			return false;
		
		return this.type.equals(type);
	}

	public boolean hasHeader() {
		return MapUtils.isNotEmpty(headers);
	}

	public boolean hasParams() {
		return MapUtils.isNotEmpty(params);
	}

	public HttpClient create() {
		return new HttpClient(this);
	}

}
