package com.fo0.twitchbot.api.rest.utils;

import com.fo0.twitchbot.utils.StreamUtils;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @created 11.10.2020 - 00:11:01
 * @author fo0 (GH:fo0)
 * @version 0.1
 */
@Log4j2
public class RestUtils {

    public static <T> Mono<T> createResponseMono(T value) {
        if (value != null) {
            return Mono.just(value);
        }

        return Mono.empty();
    }

    public static <T> Mono<T> createEmptyResponseMono() {
        return Mono.empty();
    }

    public static <T> Mono<T> createErrorResponseMono(Throwable e) {
        return Mono.error(e);
    }

    public static <T> Flux<T> createResponseFlux(Iterable<T> value) {
        if (value != null) {
            return Flux.fromStream(() -> StreamUtils.stream(value));
        }

        return Flux.empty();
    }

    public static <T> Flux<T> createEmptyResponseFlux() {
        return Flux.empty();
    }

    public static <T> Flux<T> createErrorResponseFlux(Throwable e) {
        return Flux.error(e);
    }

}
