package com.fo0.twitchbot.test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.fo0.twitchbot.bot.handler.BotAction;
import com.google.common.collect.Lists;

public class VariableFindTest {

	@Test
	public void findVariables() {
		String text = "Es ist $time $myVar abc $time2";

		Pattern pattern = Pattern.compile("\\$\\w+");
		Matcher m = pattern.matcher(text);
		List<String> results = Lists.newArrayList();
		while (m.find()) {
			results.add("found var: " + m.group());
		}
		assertEquals(3, results.size());
	}

	@Test
	public void singleVariableTest() {
		String text = "!time";

		String result = BotAction.execute(text);
		System.out.println(result);
	}

}
