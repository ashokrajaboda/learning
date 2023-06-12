package com.gagana.hospital.api.config;

import com.gagana.hospital.api.config.helper.LoggingFilterConfig;
import com.gagana.hospital.api.domain.User;
import com.gagana.hospital.api.model.CurrentUser;
import com.gagana.hospital.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ReactiveUserDetailsServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<UserDetails> findByUsername(String login) {
        log.info(this.getClass().getName());
        /*
        return Mono.defer(() -> {
                    return userRepository.findByUsername(login)
                            .filter(Objects::nonNull)
                            .switchIfEmpty(Mono.error(new BadCredentialsException(String.format("User %s not found in database", login))))
                            .map(this::createSpringSecurityUser)
                            //.contextWrite(Context.of(LoggingFilterConfig.APP_SESSION_ID,CurrentUser.getSessionId()))
                            ;
                }
        );*/
        final String sessionId = UUID.randomUUID().toString();
        return userRepository.findByUsername(login)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BadCredentialsException(String.format("User %s not found in database", login))))
                .map(user -> createSpringSecurityUser(user, sessionId))
                .contextWrite(Context.of(LoggingFilterConfig.APP_SESSION_ID, sessionId))
                ;

    }

    private  UserDetails createSpringSecurityUser(User user, String sessionId) {
        Set<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toSet());
        MDC.put(LoggingFilterConfig.APP_SESSION_ID,sessionId);
        log.debug("Creating sessionId for user : {} | {}",user.getUsername(),sessionId);
        return new CurrentUser(user.getUserId(),user.getUsername(),
                user.getPassword(), passwordEncoder.encode(sessionId),
                grantedAuthorities);
    }
}
