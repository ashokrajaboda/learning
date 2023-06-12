package com.gagana.hospital.api.config.security.jwt;

import com.gagana.hospital.api.config.helper.LoggingFilterConfig;
import com.gagana.hospital.api.model.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.UUID;

@Slf4j
public class JWTReactiveAuthorizationFilter implements WebFilter {
    private final ITokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    public JWTReactiveAuthorizationFilter(final ITokenProvider tokenProvider, final PasswordEncoder passwordEncoder) {
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isBlank(authHeader) || !StringUtils.startsWith(authHeader,"Bearer ")) {
            return chain.filter(exchange);
        }

        //try {
        var token = tokenProvider.decodeAccessToken(authHeader);
        var roles = tokenProvider.getRoles(token);
        String sessionId = tokenProvider.getDecryptedValue(token.getHeaderClaim(LoggingFilterConfig.APP_SESSION_ID).asString());
        MDC.put(LoggingFilterConfig.APP_SESSION_ID,sessionId);
        log.debug("Fetched the sessionId from token: {}",sessionId);
        var currentUser = new CurrentUser(token.getHeaderClaim(ITokenProvider.HEADER_CLAIM_USER_ID_KEY).asLong(),
                token.getSubject(),
                token.getHeaderClaim(ITokenProvider.HEADER_CLAIM_TEXT_ENCRYPTED_PWD_KEY).asString(),
                sessionId,
                roles) ;
        var auth = new UsernamePasswordAuthenticationToken(currentUser, null,roles );

        return chain.filter(exchange)
                .contextWrite(Context.of(LoggingFilterConfig.APP_SESSION_ID,sessionId))
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
        /*} catch (Exception e) {
            log.error("JWT exception", e);
        }
        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.clearContext());

         */
    }
}
