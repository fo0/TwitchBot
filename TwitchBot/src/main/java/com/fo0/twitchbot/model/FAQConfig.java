package com.fo0.twitchbot.model;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FAQConfig implements Serializable {

	private static final long serialVersionUID = 5443129074572460028L;

	@Builder.Default
	private long created = System.currentTimeMillis();
	private long updated;

	@Builder.Default
	private List<FAQItem> list = Lists.newArrayList();

}
