package com.fo0.twitchbot.model;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BotActionConfig implements Serializable {

	private static final long serialVersionUID = 5443129074572460028L;

	@Builder.Default
	private long created = System.currentTimeMillis();
	private long updated;

	@Builder.Default
	private Map<String, String> map = Maps.newHashMap();

}
