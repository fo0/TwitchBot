package com.fo0.twitchbot.system;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemInfo {

	private double cpuAverageLoad;
	private String processorArchitecture;
	private double availableProcessors;
	private String systemName;
	private double totalMemory;
	private double freeMemory;

}
