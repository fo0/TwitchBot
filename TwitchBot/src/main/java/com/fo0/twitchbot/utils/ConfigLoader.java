package com.fo0.twitchbot.utils;

import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = { "input", "output" })
@ToString(exclude = { "input", "output" })
public class ConfigLoader<T> {

	private String name;

	private Path path;

	private Supplier<T> input;
	private Consumer<T> output;

	@Setter
	@Getter
	private Consumer<T> loadListener;

	private Class<T> type;

	public ConfigLoader(@NonNull String name, Path pathToFile, Class<T> type) {
		this(name, pathToFile, type, null, null, null);
	}

	public ConfigLoader(@NonNull String name, String pathToFile, Class<T> type) {
		this(name, Paths.get(pathToFile), type, null, null, null);
	}

	public ConfigLoader(@NonNull String name, @NonNull String pathToFile, @NonNull Class<T> type, Supplier<T> input,
			Consumer<T> output) {
		this(name, Paths.get(pathToFile), type, input, output, null);
	}

	public ConfigLoader(@NonNull String name, @NonNull Path pathToFile, @NonNull Class<T> type, Supplier<T> input,
			Consumer<T> output, Consumer<T> loadListener) {
		this.name = name;
		this.path = pathToFile;
		this.type = type;
		this.input = input;
		this.output = output;
		this.loadListener = loadListener;

		init();
	}

	public void init() {
		if (!Files.exists(path)) {
			Logger.debug(String.format("creating default config for %s -> %s", name, path));
			Utils.createNewFileWithDirectories(path);

			// create default value from getter
			save();
		}

		load();
	}

	public void load() {
		Logger.debug(String.format("loading config %s -> %s", name, path));
		if (output == null) {
			Logger.error("no output listener set");
			return;
		}

		try {
			output.accept(new Gson().fromJson(new FileReader(path.toFile()), type));

			if (loadListener != null) {
				loadListener.accept(input.get());
			}
		} catch (Exception e) {
			Logger.error("failed to load file: " + path.toAbsolutePath() + " | " + e);
		}
	}

	public void save() {
		Logger.debug(String.format("saving config %s -> %s", name, path));
		if (input == null) {
			Logger.error("no input listener set");
			return;
		}

		try {
			FileUtils.write(path.toFile(), new GsonBuilder().setPrettyPrinting().create().toJson(input.get()),
					Charset.defaultCharset());
		} catch (Exception e) {
			Logger.error("failed to save file to disk: " + path.toAbsolutePath() + " | " + e);
		}
	}

}
