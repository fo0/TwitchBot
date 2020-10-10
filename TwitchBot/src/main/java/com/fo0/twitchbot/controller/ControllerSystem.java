package com.fo0.twitchbot.controller;

import com.fo0.twitchbot.system.SystemInfo;
import com.fo0.twitchbot.system.SystemInfoCollector;
import com.fo0.twitchbot.utils.Logger;

public class ControllerSystem {

	public static void init() {
		Logger.info("starting controller: system");
	}

	public static SystemInfo info() {
		return SystemInfo.builder().availableProcessors(SystemInfoCollector.getCPU())
				.cpuAverageLoad(SystemInfoCollector.getCpuAverageLoad())
				.processorArchitecture(SystemInfoCollector.getProcessorArchitecture())
				.systemName(SystemInfoCollector.getSystemName())
				.totalMemory(SystemInfoCollector.getTotalMemory())
				.freeMemory(SystemInfoCollector.getFreeMemory())
				.build();
	}
}
