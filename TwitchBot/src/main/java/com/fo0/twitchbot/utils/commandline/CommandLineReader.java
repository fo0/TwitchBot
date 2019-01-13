package com.fo0.twitchbot.utils.commandline;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.fo0.twitchbot.utils.Logger;

import lombok.Getter;
import lombok.NonNull;

public class CommandLineReader {

	private Consumer<String> listener;
	private Thread thread;

	@Getter
	private Scanner scanner;

	private boolean pause;

	public CommandLineReader(@NonNull Consumer<String> listener) {
		this.listener = listener;
	}

	public void start() {
		if (thread != null) {
			Logger.trace("IGNORE start, because its currently started");
			return;
		}

		if (scanner == null)
			scanner = new Scanner(System.in);

		thread = new Thread(() -> {
			while (true) {
				try {
					Logger.trace("Waiting for Commandline Input");
					String input = scanner.nextLine();
					Logger.trace("Console Input: " + input);

					if (pause)
						break;

					CompletableFuture cf = CompletableFuture.runAsync(() -> {
						listener.accept(input);
					});
					
					// continue listener if the action is finished
					cf.get();
				} catch (Exception e) {
					Logger.error("some error occurred at commandline-api, stopping module: " + e.getMessage());
					break;
				}
			}

			if (pause) {
				pause();
			} else {
				stop();
			}
		});

		thread.start();
	}

	public void pause() {
		Logger.debug("Commandline Api paused");
		pause = true;
		stop();
	}

	public void resume() {
		pause = false;
		start();
	}

	public void stop() {
		try {
			if (thread == null) {
				Logger.trace("IGNORE stop, because its currently not started");
				return;
			}

			if (!pause && scanner != null) {
				scanner.close();
				scanner = null;
			}

			thread.interrupt();

			thread = null;
		} catch (Exception | Error e) {
			Logger.error("some error occurred when stopping the commandline api " + e);
		}

	}

}
