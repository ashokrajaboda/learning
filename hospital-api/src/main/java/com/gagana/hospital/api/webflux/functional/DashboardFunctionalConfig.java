package com.gagana.hospital.api.webflux.functional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.sql.Timestamp;
import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DashboardFunctionalConfig {

    @Autowired
    private Sinks.Many<Object> sink;
    @Bean
    RouterFunction<ServerResponse> testData() {
        return RouterFunctions.route(RequestPredicates.GET("/webflux/test"), request -> {
            Map<String, Object> testDates = new HashMap<>();
            testDates.put("date", new Date());
            testDates.put("timestamp", new Timestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()));
            testDates.put("localDate", LocalDate.now());
            testDates.put("localDateTime", LocalDateTime.now());
            testDates.put("offsetDateTime", OffsetDateTime.now());
            testDates.put("zonedDateTime", ZonedDateTime.now());
            testDates.put("zonedDateTimeWithUserZone", ZonedDateTime.now().withZoneSameInstant(ZoneId.of(ZoneId.SHORT_IDS.get("IST"))));
            testDates.put("instant", Instant.now());
            testDates.put("nullValue", null);
            testDates.put("emptyValue", StringUtils.EMPTY);
            testDates.put("space", StringUtils.SPACE);
            return ServerResponse.ok().bodyValue(testDates);
        });
    }
}
