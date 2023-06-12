package com.gagana.hospital.api.controller;

import com.gagana.hospital.api.exception.ApiException;
import com.gagana.hospital.api.view.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/dashboard")
@Slf4j
public class DashboardController {

    @Autowired
    private DateTimeFormatter dateTimeFormatter;

    @Autowired
    private BuildProperties buildProperties;

    @Autowired
    @Qualifier("instantDateTimeFormatter")
    private DateTimeFormatter instantDateTimeFormatter;

    @Autowired
    protected Manifest manifest;

    @GetMapping("/test1")
    public Mono<String> test() {
        return Mono.just("test1");
    }




    @GetMapping("/test3")
    public Mono<ResponseDTO> test3() {
        /*
        return Mono.just(ResponseDTO.buildSuccessResponse(HttpStatus.OK, StreamSupport.stream(buildProperties.spliterator(), false)
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()))));

         */
        return Mono.just(ResponseDTO.buildSuccessResponse(manifest));
    }


    @GetMapping("/test2")
    public Mono<ResponseDTO<Map<String, Object>>> test2() {
        log.info("{} called {}",this.getClass(), Thread.currentThread().getContextClassLoader().getName());
        Map<String, Object> testDates = new HashMap<>();
        testDates.put("date", new Date());
        testDates.put("timestamp", new Timestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()));
        testDates.put("localDate", LocalDate.now());
        testDates.put("localDateISOString", LocalDate.now().format(dateTimeFormatter));
        testDates.put("localDateTime", LocalDateTime.now());
        testDates.put("localDateTimeISOString", LocalDateTime.now().format(dateTimeFormatter));
        testDates.put("offsetDateTime", OffsetDateTime.now());
        testDates.put("offsetDateTimeISOString", OffsetDateTime.now().format(dateTimeFormatter));
        testDates.put("zonedDateTime", ZonedDateTime.now());
        testDates.put("zonedDateTimeISOString", ZonedDateTime.now().format(dateTimeFormatter));
        testDates.put("zonedDateTimeISOStringInstantFormat", ZonedDateTime.now().format(instantDateTimeFormatter));
        testDates.put("instant", Instant.now());
        testDates.put("instantISOString", instantDateTimeFormatter.format(Instant.now()));
        testDates.put("zonedDateTimeWithUserZone", ZonedDateTime.now().withZoneSameInstant(ZoneId.of(ZoneId.SHORT_IDS.get("IST"))));
        return Mono.just(ResponseDTO.buildSuccessResponse(testDates))
                .publishOn(Schedulers.boundedElastic());
    }

    @GetMapping("/testException")
    public Mono<ResponseDTO<String>> testException() throws Exception {
        if(true) {
            throw new Exception("Test Exception");
        }

        return Mono.just(ResponseDTO.buildSuccessResponse(" "))
                .publishOn(Schedulers.boundedElastic());
    }

    @GetMapping("/testApiException")
    public Mono<ResponseDTO<String>> testApiException() throws Exception {
        if(true) {
            throw new ApiException("001","Test Exception");
        }
        return Mono.just(ResponseDTO.buildSuccessResponse(" "))
                .publishOn(Schedulers.boundedElastic());
    }

    @GetMapping("/testBinding")
    public Mono<ResponseDTO<String>> testBindingException(@RequestParam("value") String value) throws Exception {
        return Mono.just(ResponseDTO.buildSuccessResponse(value));
    }



    @GetMapping("/secure")
    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public Mono<String> secure() {
        return Mono.just("secure");
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Admin Access Test", security = @SecurityRequirement(name = "bearerAuth"))
    public Mono<String> admin() {
        return Mono.just("admin");
    }

    @GetMapping("/admin/role-admin-test")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Admin Access Test", security = @SecurityRequirement(name = "bearerAuth"))
    public Mono<String> roleAdmin() {
        return Mono.just("admin");
    }
}
