package com.fo0.twitchbot.api.rest;

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
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("bot")
public class BotApi extends ARestBasicTemplate {

    @Operation(summary = "default summary")
    @PostMapping("add")
    public void add(@RequestBody String twitchbotConfig) {
        executeRequestMono(() -> {
            TwitchBotConfig tbc = new Gson().fromJson(twitchbotConfig, TwitchBotConfig.class);
            ControllerTwitchBot.addBot(tbc);
        });
    }

    @Operation(summary = "default summary")
    @GetMapping("list")
    public Flux<TwitchBotConfig> list() {
        return executeRequestFlux(() -> {
            return ControllerTwitchBot.getSessionConfigs();
        });
    }

    @Operation(summary = "default summary")
    @GetMapping("find-by-id")
    public Mono<TwitchBotManager> findByID(@RequestParam(value = "id") String id) {
        return executeRequestMono(() -> {
            return ControllerTwitchBot.getBotByID(id);
        });
    }

}
