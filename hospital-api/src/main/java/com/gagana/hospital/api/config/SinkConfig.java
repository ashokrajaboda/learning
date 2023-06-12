package com.gagana.hospital.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

    @Bean
    public Sinks.Many<Object> sink() {
        return Sinks.many().replay().limit(1);
    }

    @Bean
    public Flux<Object> datesBroadcast(Sinks.Many<Object> sink) {
        return sink.asFlux();
    }
}
