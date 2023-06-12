package com.gagana.hospital.api.config.helper;

import com.gagana.hospital.api.view.Error;
import com.gagana.hospital.api.view.ErrorType;
import com.gagana.hospital.api.view.Errors;
import com.gagana.hospital.api.view.ResponseDTO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Component
public class ResponseEntityWrapper extends ResponseEntityResultHandler {

    private static MethodParameter param;

    static {
        try {
            //get new params
            param = new MethodParameter(ResponseEntityWrapper.class
                    .getDeclaredMethod("methodForParams"), -1);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static Mono<ResponseDTO> methodForParams() {
        return null;
    }

    public ResponseEntityWrapper(final ServerCodecConfigurer serverCodecConfigurer, final RequestedContentTypeResolver resolver, final ReactiveAdapterRegistry registry) {
        super(serverCodecConfigurer.getWriters(), resolver, registry);
    }

    @Override
    public boolean supports(HandlerResult result) {
        boolean isMonoOrFlux = result.getReturnType().resolve() == Mono.class
                || result.getReturnType().resolve() == Flux.class;
        boolean isAlreadyResponse = result.getReturnType().resolveGeneric(0) == ResponseDTO.class;
        return isMonoOrFlux && !isAlreadyResponse;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
        Mono<ResponseDTO> body = ((Mono<Object>) result.getReturnValue()).map(data -> new ResponseDTO(Boolean.TRUE,exchange.getResponse().getStatusCode(), data, null))
                .defaultIfEmpty(new ResponseDTO(Boolean.TRUE, exchange.getResponse().getStatusCode(),null, null));
        return writeBody(body, param, exchange);
    }
}
