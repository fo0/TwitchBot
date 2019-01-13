package com.fo0.twitchbot.test;

import org.junit.Test;

import com.fo0.twitchbot.utils.AuthFromFile;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class TranslatorTest {

	@Test
	public void englishToGerman() {
		System.out.println(AuthFromFile.getGoogleTranslatorKey());
		Translate translate = TranslateOptions.newBuilder().setApiKey(AuthFromFile.getGoogleTranslatorKey()).build()
				.getService();
		String text = "Hello, world!";
		Translation translation = translate.translate(text, TranslateOption.sourceLanguage("en"),
				TranslateOption.targetLanguage("ru"));
		System.out.println("text " + text);
		System.err.println("translated: " + translation.getTranslatedText());
	}

}
