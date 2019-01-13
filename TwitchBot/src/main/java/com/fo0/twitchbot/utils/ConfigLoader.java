package com.fo0.twitchbot.utils;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = { "input", "output" })
@ToString(exclude = { "input", "output" })
public class ConfigLoader<T> {

	private Path path;

	private Supplier<T> input;
	private Consumer<T> output;
	private Class<T> type;

	public ConfigLoader(Path pathToFile, Class<T> type) {
		this(pathToFile, type, null, null);
	}

	public ConfigLoader(String pathToFile, Class<T> type) {
		this(Paths.get(pathToFile), type, null, null);
	}

	public ConfigLoader(String pathToFile,  Class<T> type, Supplier<T> input, Consumer<T> output) {
		this(Paths.get(pathToFile), type, input, output);
	}

	public ConfigLoader(@NonNull Path pathToFile, @NonNull Class<T> type, Supplier<T> input, Consumer<T> output) {
		this.path = pathToFile;
		this.type = type;
		this.input = input;
		this.output = output;
	}

	public void init() {
		if (!path.toFile().exists()) {
			path.toFile().mkdirs();
			try {
				path.toFile().createNewFile();
			} catch (IOException e) {
			}
		}
	}

	public void load() {
		if (output == null) {
			Logger.error("no output listener set");
			return;
		}

		try {
			output.accept(new Gson().fromJson(new FileReader(path.toFile()), type));
		} catch (Exception e) {
			Logger.error("failed to load file: " + path.toAbsolutePath() + " | " + e);
		}
	}

	public void save() {
		if (input == null) {
			Logger.error("no input listener set");
			return;
		}

		try {
			FileUtils.write(path.toFile(), new Gson().toJson(input.get()), Charset.defaultCharset());
		} catch (Exception e) {
			Logger.error("failed to save file to disk: " + path.toAbsolutePath() + " | " + e);
		}

	}
}
