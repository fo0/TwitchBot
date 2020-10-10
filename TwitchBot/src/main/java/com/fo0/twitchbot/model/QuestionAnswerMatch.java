package com.fo0.twitchbot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionAnswerMatch {

	private QuestionMatch question;
	private String answer;

	public String info() {
		return String.format("Question: %s [%s]", (question != null ? question.getQuestion() : null),
				(question != null ? question.getScore() : null), answer);
	}
}
