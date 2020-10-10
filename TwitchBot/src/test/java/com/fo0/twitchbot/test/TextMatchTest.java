package com.fo0.twitchbot.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import com.fo0.twitchbot.model.QuestionMatch;
import com.fo0.twitchbot.model.QuestionStore;

public class TextMatchTest {

	@Test
	public void matchTest() {
		JaroWinklerDistance d = new JaroWinklerDistance();
		double match = d.apply("hello", "hello");
		System.out.println("match: " + match);
		assertEquals(1.0d, match, 0);
	}

	@Test
	public void questionStoreTest() {
		QuestionStore store = new QuestionStore(Arrays.asList("hallo", "hello", "bananas"));
		QuestionMatch question = store.getQuestion("ban");
		System.out.println(question);
		assertEquals("bananas", question.getQuestion());
		assertThat(0.8666666666666668, CoreMatchers.is(question.getScore()));
	}

}
