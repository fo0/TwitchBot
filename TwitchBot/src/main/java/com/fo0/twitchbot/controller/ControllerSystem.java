package com.fo0.twitchbot.controller;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fo0.twitchbot.system.SystemInfo;
import com.fo0.twitchbot.system.SystemInfoCollector;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ControllerSystem {

    @PostConstruct
    public static void init() {
        log.info("starting system controller");

    }

    public SystemInfo info() {
        return SystemInfo.builder()
                         .availableProcessors(SystemInfoCollector.getCPU())
                         .cpuAverageLoad(SystemInfoCollector.getCpuAverageLoad())
                         .processorArchitecture(SystemInfoCollector.getProcessorArchitecture())
                         .systemName(SystemInfoCollector.getSystemName())
                         .totalMemory(SystemInfoCollector.getTotalMemory())
                         .freeMemory(SystemInfoCollector.getFreeMemory())
                         .build();
    }
}
