package com.gagana.hospital.api.config.helper;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilterConfig implements WebFilter {
    public static final String APP_SESSION_ID = "X-Session-Id";
    public static final String APP_TXN_ID = "X-Txn-Id";

    public static final List<String> MDC_LIST = Collections.unmodifiableList(Arrays.asList(APP_SESSION_ID,APP_TXN_ID));

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String txnId = getTxnId(request.getHeaders());
        MDC.put(APP_TXN_ID,txnId);
        log.debug("Creating txn Id : {}",txnId);
        return chain
                .filter(exchange)
                .contextWrite(Context.of(APP_TXN_ID,txnId))
                .doOnTerminate(MDC::clear);
    }

    private String getTxnId(HttpHeaders headers) {
        List<String> requestIdHeaders = headers.get(APP_TXN_ID);
        return CollectionUtils.isEmpty(requestIdHeaders)
                ? UUID.randomUUID().toString()
                : requestIdHeaders.get(0);
    }
}