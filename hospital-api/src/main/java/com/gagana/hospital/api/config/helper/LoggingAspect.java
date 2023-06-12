package com.gagana.hospital.api.config.helper;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Around("within(@org.springframework.stereotype.Controller *) || within(@org.springframework.stereotype.Service *) || execution(public !void org.springframework.data.repository.Repository+.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Request {} {} - {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
            }
            Object result = joinPoint.proceed();

            if (log.isDebugEnabled()) {
                if (result instanceof Mono) {
                    var monoResult = (Mono<?>) result;

                    return monoResult.doOnSuccess(o -> {
                        var response = "";
                        if (Objects.nonNull(o)) {
                            response = o.toString();
                        }
                        log.debug("Response {} {} - {}",
                                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                                response);
                    });
                }if (result instanceof Flux) {
                    var fluxResult = (Flux<?>) result;
                    return fluxResult.map(fluxItem -> {
                        log.debug("Response {} {} - {}", joinPoint.getSignature().getDeclaringTypeName(),
                                joinPoint.getSignature().getName(), fluxItem);
                        return fluxItem;
                    });

                } else {
                    log.debug("Response {} {} - {}", joinPoint.getSignature().getDeclaringTypeName(),
                            joinPoint.getSignature().getName(), result);
                }
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Exception {} {} {}", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), e);
            throw e;
        }
    }
}
