package com.gagana.hospital.api.config.security.jwt;

import com.gagana.hospital.api.config.properties.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
public class JWTConfig {

    @Bean
    public AuthJwtTokenProvider authJwtTokenProvider(final SecurityProperties securityProperties, final TextEncryptor textEncryptor) {
        return new AuthJwtTokenProvider(securityProperties, textEncryptor);
    }
}
