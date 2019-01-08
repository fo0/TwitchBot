package com.fo0.twitchbot.model;

import java.io.Serializable;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TwitchBotAction implements Serializable {

	private static final long serialVersionUID = -6946773889977938593L;

	@Builder.Default
	private String id = RandomStringUtils.randomAlphabetic(10);

	private String action;
	
	private String value;

}
