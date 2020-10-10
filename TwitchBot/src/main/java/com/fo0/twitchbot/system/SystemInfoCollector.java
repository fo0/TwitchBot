package com.fo0.twitchbot.system;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class SystemInfoCollector {

	private static final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

	public static double getCpuAverageLoad() {
		return osBean.getSystemLoadAverage();
	}

	public static String getProcessorArchitecture() {
		return osBean.getArch();
	}

	public static double getCPU() {
		return osBean.getAvailableProcessors();
	}

	public static String getSystemName() {
		return osBean.getName();
	}

	public static double getTotalMemory() {
		return ((double) (Runtime.getRuntime().totalMemory() / 1024) / 1024);
	}

	public static double getFreeMemory() {
		return ((double) (Runtime.getRuntime().freeMemory() / 1024) / 1024);
	}

	public static double getCurrentMemoryUsage() {
		return getTotalMemory() - getFreeMemory();
	}

}
