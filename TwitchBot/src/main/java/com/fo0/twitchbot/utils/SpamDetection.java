package com.fo0.twitchbot.utils;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

import lombok.NonNull;

public class SpamDetection {

	private Thread thread = null;
	private long interval = TimeUnit.SECONDS.toMillis(2);
	private long treshold = 5;

	private Map<String, Long> map = Maps.newConcurrentMap();

	private Consumer<Map<String, Long>> listener;

	public SpamDetection(long interval, long treshold, @NonNull Consumer<Map<String, Long>> listener) {
		this.listener = listener;
		this.interval = interval;
		this.treshold = treshold;
		start();
	}

	public SpamDetection(@NonNull Consumer<Map<String, Long>> listener) {
		this.listener = listener;
	}

	public void addUser(String user) {
		if (StringUtils.isBlank(user)) {
			return;
		}

		if (map.containsKey(user)) {
			map.put(user, map.get(user) + 1);
		} else {
			map.put(user, 1L);
		}
	}

	public void start() {
		if (thread != null) {
			return;
		}

		thread = new Thread(() -> {
			while (true) {
				Utils.sleep(interval);
				detectSpam();
			}
		});
		thread.start();
	}

	public void stop() {
		if (thread == null) {
			return;
		}

		thread.interrupt();

		thread = null;
	}

	private void detectSpam() {
		if (MapUtils.isEmpty(map)) {
			Logger.trace("spam detect counter empty");
			return;
		}

		Logger.trace("start spam detect");
		Map<String, Long> spammers = map.entrySet().stream().filter(e -> e.getValue() >= treshold)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		Logger.debug("spam detect result: " + spammers.size());

		if (MapUtils.isNotEmpty(spammers))
			listener.accept(spammers);

		map.clear();
	}
}
