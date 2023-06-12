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
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Component
public class ResponseBodyWrapper extends ResponseBodyResultHandler {

    private static MethodParameter param;

    static {
        try {
            //get new params
            param = new MethodParameter(ResponseBodyWrapper.class
                    .getDeclaredMethod("methodForParams"), -1);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static Mono<ResponseDTO> methodForParams() {
        return null;
    }
    public ResponseBodyWrapper(final ServerCodecConfigurer serverCodecConfigurer, final RequestedContentTypeResolver resolver, final ReactiveAdapterRegistry registry) {
        super(serverCodecConfigurer.getWriters(), resolver, registry);
    }

    @Override
    public boolean supports(HandlerResult result) {
        boolean isMono = result.getReturnType().resolve() == Mono.class;
        boolean isAlreadyResponse = result.getReturnType().resolveGeneric(0) == ResponseDTO.class;
        return isMono && !isAlreadyResponse;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
        var returnValue = result.getReturnValue();
        Mono<?> returnMono = null;
        if(returnValue instanceof Mono<?> ) {
            returnMono = (Mono<?>) returnValue;
        } else if (returnValue instanceof Flux<?>) {
            returnMono = ((Flux<?>) returnValue).collectList();
        } else {
            throw new ClassCastException("The \"body\" should be Mono<*> or Flux<*>!");
        }
        Mono<ResponseDTO> response = returnMono.map(r -> new ResponseDTO(Boolean.TRUE, exchange.getResponse().getStatusCode(), r, null))
                //.onErrorMap(Exception.class, (exception) -> new Response(Boolean.FALSE, null, processErrors(HttpStatus.INTERNAL_SERVER_ERROR, exception)))
                .onErrorResume(e -> Mono.just(new ResponseDTO(Boolean.FALSE,exchange.getResponse().getStatusCode(), null, processErrors( e))));

        return writeBody(response, param, exchange);
    }

    private Errors processErrors(Throwable... e) {
        return Errors.builder()
                .errorList(buildErrors(e))
                .build();
    }

    private List<Error> buildErrors(Throwable... exceptions) {
        return Stream.of(exceptions).map(e -> {
            return  Error.builder().errorMessage(ExceptionUtils.getRootCauseMessage(e))
                    .errorType(ErrorType.ERROR)
                    .build();
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
