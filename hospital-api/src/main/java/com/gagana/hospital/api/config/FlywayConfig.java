package com.gagana.hospital.api.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Order(value = 3)
@Configuration
@Slf4j
public class FlywayConfig {

    private final Environment env;

    private final TextEncryptor textEncryptor;

    private final FlywayProperties flywayProperties;

    public FlywayConfig(final Environment env,final TextEncryptor textEncryptor,final FlywayProperties flywayProperties) {
        this.env = env;
        this.textEncryptor = textEncryptor;
        this.flywayProperties = flywayProperties;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        log.info("Encrypted Password : {}", textEncryptor.encrypt(flywayProperties.getPassword()));
        var flyway = new Flyway(
                Flyway.configure()
                        .baselineOnMigrate(false)
                        .encoding(StandardCharsets.UTF_8)
                        .defaultSchema(flywayProperties.getDefaultSchema())
                        .locations(flywayProperties.getLocations().stream().toArray(String[]::new))
                        .dataSource(flywayProperties.getUrl(),
                                flywayProperties.getUser(),
                                textEncryptor.decrypt(flywayProperties.getPassword())
                                //"changeme"
                        ));

        log.info("FLYWAY LOCATIONS IS {}", Arrays.stream(flyway.getConfiguration().getLocations()).toArray());
        return flyway;
    }
}
