package com.fo0.twitchbot.model;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.textmatch.TextMatchUtils;
import com.google.common.collect.Sets;

import lombok.Getter;

public class QuestionStore {

	@Getter
	private Set<String> store = Sets.newConcurrentHashSet();

	@Getter
	private double matchScore = 0.80d;

	public QuestionStore(Collection<String> questions) {
		this.store.addAll(questions);
		this.matchScore = 0.80d;
	}

	public QuestionStore(String questions) {
		this.store.add(questions);
		this.matchScore = 0.80d;
	}

	public void remove(String question) {
		store.remove(question);
	}

	public void add(String question) {
		store.add(question);
	}

	public void add(Collection<String> questions) {
		store.addAll(questions);
	}

	public boolean containsQuestion(String question) {
		return store.parallelStream().anyMatch(e -> contains(e, question));
	}

	public QuestionMatch getQuestion(String question) {
		return store.stream().map(e -> QuestionMatch.builder().question(e).score(TextMatchUtils.match(e, question)).build())
				.sorted((e1, e2) -> Double.compare(e2.getScore(), e1.getScore())).findFirst().orElse(null);
	}

	public QuestionStore setMatchScore(double score) {
		this.matchScore = score;
		return this;
	}

	private boolean match(String source, String destination) {
		double match = TextMatchUtils.match(source, destination);
		return Double.compare(match, matchScore) > 0;
	}

	private boolean contains(String source, String destination) {
		if (Double.compare(matchScore, 1.00d) == 0) {
			// distance match disabled
			return StringUtils.containsIgnoreCase(source, destination);
		}

		return match(source, destination);
	}

}
