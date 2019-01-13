package com.fo0.twitchbot.model;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TwitchChatMessage {

	@Builder.Default
	private String id = RandomStringUtils.randomAlphabetic(30);

	private String name;
	private String message;

}
