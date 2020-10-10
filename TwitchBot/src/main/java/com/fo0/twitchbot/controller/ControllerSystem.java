package com.fo0.twitchbot.controller;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fo0.twitchbot.system.SystemInfo;
import com.fo0.twitchbot.system.SystemInfoCollector;
import com.fo0.twitchbot.utils.Logger;

@Component
public class ControllerSystem {

    @PostConstruct
	public static void init() {
		Logger.info("starting controller: system");
	}

	public SystemInfo info() {
		return SystemInfo.builder().availableProcessors(SystemInfoCollector.getCPU())
				.cpuAverageLoad(SystemInfoCollector.getCpuAverageLoad())
				.processorArchitecture(SystemInfoCollector.getProcessorArchitecture())
				.systemName(SystemInfoCollector.getSystemName())
				.totalMemory(SystemInfoCollector.getTotalMemory())
				.freeMemory(SystemInfoCollector.getFreeMemory())
				.build();
	}
}
