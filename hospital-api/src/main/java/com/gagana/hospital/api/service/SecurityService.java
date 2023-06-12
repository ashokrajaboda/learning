package com.gagana.hospital.api.service;

import com.gagana.hospital.api.config.properties.SecurityProperties;
import com.gagana.hospital.api.config.security.jwt.AuthJwtTokenProvider;
import com.gagana.hospital.api.config.security.jwt.ITokenProvider;
import com.gagana.hospital.api.model.CurrentUser;
import com.gagana.hospital.api.model.TokenInfo;
import com.gagana.hospital.api.view.LoginRequest;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SecurityService implements ISecurityService{

    private final ReactiveAuthenticationManager authenticationManager;
    private final ITokenProvider tokenProvider;
    private final Validator validator;
    private final SecurityProperties securityProperties;

    public SecurityService(final ReactiveAuthenticationManager authenticationManager, final ITokenProvider tokenProvider,
                           final Validator validator, final SecurityProperties securityProperties) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.validator = validator;
        this.securityProperties = securityProperties;
    }

    @Override
    public Mono<TokenInfo> authenticate(final LoginRequest loginRequest) {
        if (!this.validator.validate(loginRequest).isEmpty()) {
            return Mono.error(new RuntimeException("Bad request"));
        }

        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Mono<Authentication> authentication = this.authenticationManager.authenticate(authenticationToken);
        authentication.doOnError(throwable -> {
            throw new BadCredentialsException("Bad crendentials");
        });
        ReactiveSecurityContextHolder.withAuthentication(authenticationToken);

        return authentication.map(auth -> {
            log.info("Auth : {}",auth.getPrincipal());
            var currentUser = (CurrentUser) auth.getPrincipal();
            TokenInfo token = tokenProvider.generateToken(currentUser.getUsername(),currentUser.getRoles(),tokenProvider.buildHeaderClaims(currentUser));
            return token;
        });
    }
}
