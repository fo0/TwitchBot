package com.fo0.twitchbot.textmatch;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.JaroWinklerDistance;

public class TextMatchUtils {

	/**
	 * blank input has no match
	 */
	public static double match(String source, String destination) {
		if (StringUtils.isAnyBlank(source, destination)) {
			return 0.0;
		}

		JaroWinklerDistance d = new JaroWinklerDistance();
		return d.apply(source, destination);
	}

}
