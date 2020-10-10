package com.fo0.twitchbot.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fo0.twitchbot.api.rest.utils.ARestBasicTemplate;
import com.fo0.twitchbot.bot.TwitchBotManager;
import com.fo0.twitchbot.controller.ControllerTwitchBot;
import com.fo0.twitchbot.model.TwitchBotConfig;
import com.google.gson.Gson;

import io.swagger.v3.oas.annotations.Operation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("bot")
public class BotApi extends ARestBasicTemplate {

    @Autowired
    private ControllerTwitchBot controller;

    @Operation(summary = "default summary")
    @PostMapping("add")
    public void add(@RequestBody String twitchbotConfig) {
        executeRequestMono(() -> {
            TwitchBotConfig tbc = new Gson().fromJson(twitchbotConfig, TwitchBotConfig.class);
            controller.addBot(tbc);
        });
    }

    @Operation(summary = "default summary")
    @GetMapping("list")
    public Flux<TwitchBotConfig> list() {
        return executeRequestFlux(() -> {
            return controller.getSessionConfigs();
        });
    }

    @Operation(summary = "default summary")
    @GetMapping("find-by-id")
    public Mono<TwitchBotManager> findByID(@RequestParam(value = "id") String id) {
        return executeRequestMono(() -> {
            return controller.getBotByID(id);
        });
    }

}
