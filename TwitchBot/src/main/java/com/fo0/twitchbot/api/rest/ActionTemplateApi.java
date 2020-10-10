package com.fo0.twitchbot.api.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fo0.twitchbot.api.rest.utils.ARestBasicTemplate;
import com.fo0.twitchbot.bot.template.ActionDefaultTemplate;
import com.fo0.twitchbot.bot.template.FAQStoreTemplate;
import com.fo0.twitchbot.model.BotActionConfig;
import com.fo0.twitchbot.model.FAQConfig;

import io.swagger.v3.oas.annotations.Operation;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("template")
public class ActionTemplateApi extends ARestBasicTemplate {

    @Operation(summary = "default summary")
    @GetMapping("action-template")
    public Mono<BotActionConfig> actionTemplateList() {
        return executeRequestMono(() -> {
            return ActionDefaultTemplate.getCONFIG();
        });
    }

    @Operation(summary = "default summary")
    @GetMapping("faq-template")
    public Mono<FAQConfig> faqStoreTemplateList() {
        return executeRequestMono(() -> {
            return FAQStoreTemplate.getCONFIG();
        });
    }

}
