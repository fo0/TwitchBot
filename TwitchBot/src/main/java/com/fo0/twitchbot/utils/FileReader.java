package com.fo0.twitchbot.utils;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fo0.twitchbot.model.KeyValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FileReader {

	public static List<KeyValue> read(Path path) {
		String json = null;
		try {
			json = FileUtils.readFileToString(path.toFile(), Charset.defaultCharset());
		} catch (Exception e) {
			Logger.error("Failed to read json file: " + e.getMessage());
		}

		return new Gson().fromJson(json, TypeToken.getParameterized(ArrayList.class, KeyValue.class).getType());
	}

}
