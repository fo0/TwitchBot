package com.fo0.twitchbot.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KeyValue {

	private String key;
	private String value;
	
}
