package com.fo0.twitchbot.model;

import java.io.Serializable;

import org.apache.commons.lang3.RandomStringUtils;

import com.fo0.twitchbot.bot.EBotAction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TwitchBotAction implements Serializable {

	private static final long serialVersionUID = -6946773889977938593L;

	@Builder.Default
	private String id = RandomStringUtils.randomAlphabetic(10);

	@Builder.Default
	private String action = EBotAction.ChatMessage.name();
	
	private String toUser;
	
	private String value;

}
