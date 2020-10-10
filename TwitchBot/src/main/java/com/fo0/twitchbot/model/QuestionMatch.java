package com.fo0.twitchbot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionMatch {

	private String question;
	private double score;

}
