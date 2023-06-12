package com.gagana.hospital.api.config;

import com.gagana.hospital.api.config.helper.LoggingFilterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
/*
@Configuration
@Slf4j
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> router() {

        return RouterFunctions
                .route(GET("/test/mdc"), serverRequest -> {
                    log.info("log in start");

                    Flux<String> flux = Flux.just("test")
                            .doOnNext(s -> log.info("log in doOnNext"))
                            .map(s -> {
                                log.info("log in map");
                                return s;
                            })
                            .flatMap(s ->
                            {
                                log.info("log in flatMap");
                                return Mono.subscriberContext().map(c -> {
                                    log.info("log in subscriberContext");
                                    return s + " " + c.getOrDefault(LoggingFilterConfig.APP_TXN_ID, "no_data");
                                });
                            })
                            .subscriberContext(Context.of(LoggingFilterConfig.APP_TXN_ID, "context_data "+ System.currentTimeMillis()));

                    log.info("log in end");

                    return ServerResponse
                            .ok()
                            .body(flux, String.class);
                });
    }
}
 */
