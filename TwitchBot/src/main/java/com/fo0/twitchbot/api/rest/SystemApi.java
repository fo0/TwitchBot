package com.fo0.twitchbot.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fo0.twitchbot.api.rest.utils.ARestBasicTemplate;
import com.fo0.twitchbot.controller.ControllerSystem;
import com.fo0.twitchbot.system.SystemInfo;

import io.swagger.v3.oas.annotations.Operation;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("system")
public class SystemApi extends ARestBasicTemplate {

    @Autowired
    private ControllerSystem controller;

    @Operation(summary = "default summary")
    @GetMapping("info")
    public Mono<SystemInfo> info() {
        return executeRequestMono(() -> {
            return controller.info();
        });
    }

}
