package com.gagana.hospital.api.exception;

import com.gagana.hospital.api.config.helper.LoggingFilterConfig;
import com.gagana.hospital.api.utils.AppUtils;
import com.gagana.hospital.api.view.Error;
import com.gagana.hospital.api.view.ErrorType;
import com.gagana.hospital.api.view.Errors;
import com.gagana.hospital.api.view.ResponseDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
@Order(-2)
@Slf4j
public class ApiExceptionHandler extends DefaultErrorWebExceptionHandler {


    /**
     * Create a new {@code DefaultErrorWebExceptionHandler} instance.
     *
     * @param errorAttributes    the error attributes
     * @param resources          the resources configuration properties
     * @param errorProperties    the error configuration properties
     * @param applicationContext the current application context
     * @since 2.4.0
     */
    public ApiExceptionHandler(ErrorAttributes errorAttributes,
                               WebProperties.Resources resources,
                               ErrorProperties errorProperties,
                               ApplicationContext applicationContext,
                               ServerCodecConfigurer serverCodecConfigurer
    ) {
        super(errorAttributes, resources, errorProperties, applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", new Date());
        errorAttributes.put("path", request.path());
        Throwable error = getError(request);
        MergedAnnotation<ResponseStatus> responseStatusAnnotation = MergedAnnotations
                .from(error.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class);
        HttpStatusCode errorStatus = determineHttpStatus(error, responseStatusAnnotation,request);
        errorAttributes.put("status", errorStatus.value());
        errorAttributes.put("error", errorStatus);
        errorAttributes.put("message", ExceptionUtils.getRootCauseMessage(error));
        errorAttributes.put("requestId", request.exchange().getRequest().getId());
        errorAttributes.put(LoggingFilterConfig.APP_TXN_ID, MDC.get(LoggingFilterConfig.APP_TXN_ID));

        handleException(errorAttributes, determineException(error), options.isIncluded(ErrorAttributeOptions.Include.STACK_TRACE));
        return errorAttributes;
    }

    private void handleException(Map<String, Object> errorAttributes, Throwable error, boolean includeStackTrace) {
        errorAttributes.put("exception", error.getClass().getName());
        if (includeStackTrace) {
            addStackTrace(errorAttributes, error);
        }
        if (error instanceof BindingResult) {
            BindingResult result = (BindingResult) error;
            if (result.hasErrors()) {
                errorAttributes.put("errors", result.getAllErrors());
            }
        }
    }

    private HttpStatusCode determineHttpStatus(Throwable error, MergedAnnotation<ResponseStatus> responseStatusAnnotation, ServerRequest serverRequest) {
        if (error instanceof ResponseStatusException) {
            return ((ResponseStatusException) error).getStatusCode();
        }
        if(AppUtils.resolve(() ->serverRequest.exchange().getResponse().getStatusCode(),null).isPresent() && !serverRequest.exchange().getResponse().getStatusCode().is2xxSuccessful()) {
            return serverRequest.exchange().getResponse().getStatusCode();
        }
        return responseStatusAnnotation.getValue("code", HttpStatus.class).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Throwable determineException(Throwable error) {
        if (error instanceof ResponseStatusException) {
            return (error.getCause() != null) ? error.getCause() : error;
        }
        return error;
    }
    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> error = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
        HttpStatus httpStatus= HttpStatus.valueOf(getHttpStatus(error));
        ResponseDTO<Map<String, Object>> responseDTO = ResponseDTO.buildResponse(Boolean.FALSE, httpStatus, error, processErrors(this.getError(request)));
        return ServerResponse.status(httpStatus).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(responseDTO));
    }

    private void addStackTrace(Map<String, Object> errorAttributes, Throwable error) {
        StringWriter stackTrace = new StringWriter();
        error.printStackTrace(new PrintWriter(stackTrace));
        stackTrace.flush();
        errorAttributes.put("trace", stackTrace.toString());
    }

/*
    @ExceptionHandler(ApiException.class)
    public Mono<ResponseDTO<Void>> apiExceptionHandler(ApiException ex) {
        return Mono.just(ResponseDTO.buildFailureResponse(HttpStatus.BAD_REQUEST, processErrors(ex)));
    }
 */

    /*
    @ExceptionHandler(Exception.class)
    public Mono<ResponseDTO<Void>> defaultExceptionHandler(Exception ex) {
        return Mono.just(ResponseDTO.buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, processErrors(ex)));
    }
     */


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
