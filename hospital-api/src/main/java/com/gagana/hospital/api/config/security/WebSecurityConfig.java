package com.gagana.hospital.api.config.security;

import com.gagana.hospital.api.config.properties.SecurityProperties;
import com.gagana.hospital.api.config.security.jwt.ITokenProvider;
import com.gagana.hospital.api.config.security.jwt.JWTReactiveAuthorizationFilter;
import com.gagana.hospital.api.domain.Authorities;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity(proxyTargetClass = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig {
    private final SecurityProperties securityProperties;

    public WebSecurityConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    public ServerSecurityContextRepository serverSecurityContextRepository() {
        return NoOpServerSecurityContextRepository.getInstance();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService reactiveUserDetailsService, PasswordEncoder passwordEncoder) {
        var reactiveAuthenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
        reactiveAuthenticationManager.setPasswordEncoder(passwordEncoder);
        return reactiveAuthenticationManager;
    }

    //@Bean
    public AuthenticationWebFilter authenticationWebFilter(ReactiveAuthenticationManager reactiveAuthenticationManager
                                                           , ServerSecurityContextRepository serverSecurityContextRepository
                                                           , ServerAuthenticationConverter jwtConverter
                                                           //, ServerAuthenticationSuccessHandler serverAuthenticationSuccessHandler,
                                                           //, ServerAuthenticationFailureHandler jwtServerAuthenticationFailureHandler
                                                           ) {
        var authenticationWebFilter = new AuthenticationWebFilter(reactiveAuthenticationManager);
        authenticationWebFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));
        authenticationWebFilter.setServerAuthenticationConverter(jwtConverter);
        //authenticationWebFilter.setAuthenticationSuccessHandler(this.);
        //authenticationWebFilter.setAuthenticationFailureHandler(jwtServerAuthenticationFailureHandler);
        authenticationWebFilter.setSecurityContextRepository(serverSecurityContextRepository);
        return authenticationWebFilter;
    }

    @Bean
    public SecurityWebFilterChain configureSecurity(ServerHttpSecurity http
                                                    , ServerSecurityContextRepository serverSecurityContextRepository
                                                    , ReactiveAuthenticationManager reactiveAuthenticationManager
                                                    //,AuthenticationWebFilter authenticationWebFilter
                                                    , ITokenProvider tokenProvider,
                                                    PasswordEncoder passwordEncoder
    ) {
        http.httpBasic().disable()
                .formLogin().disable()
                .csrf()
                    .disable()
                //.csrf().csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
                .logout()
                .disable()
                .headers()
                .and()
                .cors()
                .configurationSource(corsConfigurationSource())
                //.frameOptions().disable()
                //.cache().disable()
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedHandler)
                .accessDeniedHandler(this.accessDeniedHandler);

        http.authorizeExchange()
                .pathMatchers(securityProperties.getAuthWhiteList().toArray(String[]::new)).permitAll()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/admin/**").hasAnyAuthority(Authorities.ADMIN.name())
                .anyExchange().authenticated();

        http    .authenticationManager(reactiveAuthenticationManager)
                //.addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(new JWTReactiveAuthorizationFilter(tokenProvider, passwordEncoder), SecurityWebFiltersOrder.AUTHORIZATION)
                .securityContextRepository(serverSecurityContextRepository);
        return http.build();

    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        // corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource ccs = new UrlBasedCorsConfigurationSource();
        ccs.registerCorsConfiguration("/**", corsConfiguration);
        return ccs;
    }

    private ServerAuthenticationEntryPoint unauthorizedHandler = (swe, e) -> {
        return Mono.fromRunnable(() -> {
            //log.info(e.getMessage(),e);
            swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            //throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ExceptionUtils.getRootCauseMessage(e),e);
            throw e;
        });
    };

    private ServerAccessDeniedHandler accessDeniedHandler = (swe, e) -> {
        return Mono.fromRunnable(() -> {
            //log.info(e.getMessage(),e);
            swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            //throw new ResponseStatusException(HttpStatus.FORBIDDEN, ExceptionUtils.getRootCauseMessage(e),e);
            throw e;
        });
    };
}
