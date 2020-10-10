package com.fo0.twitchbot.model;

import java.io.Serializable;
import java.util.List;

import com.google.api.client.util.Lists;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TwitchBotConfigs implements Serializable {

	private static final long serialVersionUID = -287385262998839225L;

	@Builder.Default
	private long created = System.currentTimeMillis();
	private long updated;

	@Builder.Default
	private List<TwitchBotConfig> list = Lists.newArrayList();

}
