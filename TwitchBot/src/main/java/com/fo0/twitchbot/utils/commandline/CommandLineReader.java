package com.fo0.twitchbot.utils.commandline;

import java.util.Scanner;
import java.util.function.Consumer;

import com.fo0.twitchbot.utils.Logger;

import lombok.NonNull;

public class CommandLineReader {

	private Consumer<String> listener;
	private Thread thread;
	private Scanner scanner;

	public CommandLineReader(@NonNull Consumer<String> listener) {
		this.listener = listener;
	}

	public void start() {
		if (thread != null) {
			Logger.trace("IGNORE start, because its currently started");
			return;
		}

		thread = new Thread(() -> {
			scanner = new Scanner(System.in);
			while (true) {
				try {
					Logger.trace("Waiting for Commandline Input");
					String input = scanner.next();
					Logger.trace("Console Input: " + input);
					listener.accept(input);
				} catch (Exception e) {
					Logger.error("some error occurred at commandline-api, stopping module: "+e.getMessage());
					break;
				}
			}
			
			stop();
		});

		thread.start();
	}

	public void stop() {
		if (thread == null) {
			Logger.trace("IGNORE stop, because its currently not started");
			return;
		}

		if (scanner != null) {
			scanner.close();
			scanner = null;
		}

		thread.interrupt();

		thread = null;
	}

}
