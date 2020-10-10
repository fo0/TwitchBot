package com.fo0.twitchbot.api.rest.utils;

import org.apache.commons.collections4.CollectionUtils;

import com.fo0.twitchbot.interfaces.ExceptionalRunnable;
import com.fo0.twitchbot.interfaces.ExceptionalSupplier;
import com.fo0.twitchbot.utils.StackTraceUtils;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @created 11.10.2020 - 00:07:38
 * @author fo0 (GH:fo0)
 * @version 0.1
 */
@Log4j2
public class ARestBasicTemplate {

    public <T> Flux<T> executeRequestFlux(ExceptionalSupplier<Iterable<T>> request) {
        try {
            Iterable<T> data = request.get();

            if (log.isDebugEnabled()) {
                int collectionCount = CollectionUtils.size(data);
                log.debug(String.format("api caller: %s | collection-count: %s | data: %s", StackTraceUtils.methodCaller(1), data != null, collectionCount));
            } else if (log.isTraceEnabled()) {
                int collectionCount = CollectionUtils.size(data);
                log.trace(String.format("api caller: %s | collection-count: %s | data: %s", StackTraceUtils.methodCaller(1), collectionCount, data));
            }

            return RestUtils.createResponseFlux(data);
        } catch (Exception | Error e) {
            log.error(String.format("failed to process request in api %s", StackTraceUtils.methodCaller(1), e), e);
        }

        return RestUtils.createEmptyResponseFlux();
    }

    public void executeRequestFlux(ExceptionalRunnable request) {
        try {
            request.run();

            if (log.isDebugEnabled()) {
                log.debug(String.format("api caller: %s", StackTraceUtils.methodCaller(1)));
            }

        } catch (Exception | Error e) {
            log.error(String.format("failed to process request in api %s", StackTraceUtils.methodCaller(1), e), e);
        }
    }

    public <T> Mono<T> executeRequestMono(ExceptionalSupplier<T> request) {
        try {
            T data = request.get();

            if (log.isDebugEnabled()) {
                log.debug(String.format("api caller: %s | data: %s", StackTraceUtils.methodCaller(1), data != null));
            } else if (log.isTraceEnabled()) {
                log.trace(String.format("api caller: %s | data: %s", StackTraceUtils.methodCaller(1), data));
            }

            return RestUtils.createResponseMono(data);
        } catch (Exception | Error e) {
            log.error(String.format("failed to process request in api %s", StackTraceUtils.methodCaller(1), e), e);
        }

        return RestUtils.createEmptyResponseMono();
    }

    public void executeRequestMono(ExceptionalRunnable request) {
        try {
            request.run();

            if (log.isDebugEnabled()) {
                log.debug(String.format("api caller: %s", StackTraceUtils.methodCaller(1)));
            }

        } catch (Exception | Error e) {
            log.error(String.format("failed to process request in api %s", StackTraceUtils.methodCaller(1), e), e);
        }
    }

}