package com.fo0.twitchbot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FAQItem {

	private QuestionStore question;
	private String answer;

}
