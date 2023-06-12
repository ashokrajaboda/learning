package com.gagana.hospital.api.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

@Slf4j
public class ReactiveAuthenticationManagerImpl extends UserDetailsRepositoryReactiveAuthenticationManager implements ReactiveAuthenticationManager {
    private final ReactiveUserDetailsService reactiveUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    public ReactiveAuthenticationManagerImpl(ReactiveUserDetailsService reactiveUserDetailsService,PasswordEncoder passwordEncoder) {
        super(reactiveUserDetailsService);
        this.reactiveUserDetailsService = reactiveUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.info(this.getClass().getName());
        return super.authenticate(authentication);
    }
}
