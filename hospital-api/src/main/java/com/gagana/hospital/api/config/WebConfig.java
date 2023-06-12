package com.gagana.hospital.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gagana.hospital.api.config.properties.R2dbcDatabaseProperties;
import com.gagana.hospital.api.config.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.AbstractJackson2Decoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

@Order(value = 1)
@Configuration
@AutoConfigureBefore({DatabaseConfig.class,FlywayConfig.class})
//Dont Add EnableWebFlux: Its overides ObjectMapper and date in array of numbers
//@EnableWebFlux
@EnableAsync
@EnableCaching
@EnableR2dbcAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@Slf4j
public class WebConfig implements WebFluxConfigurer {

    private static final int DEFAULT_SALT_LENGTH = 16;

    private static final int DEFAULT_HASH_LENGTH = 64;

    private static final int DEFAULT_PARALLELISM = 1;

    private static final int DEFAULT_MEMORY = 1 << 12;

    private static final int DEFAULT_ITERATIONS = 7;

    private static final int SALT_BYTE_SIZE = 32;

    @Bean
    @ConfigurationProperties(prefix = "application.security")
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }
    @Bean
    public PasswordEncoder passwordEncoder(SecurityProperties securityProperties) {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("argon2", new Argon2PasswordEncoder(DEFAULT_SALT_LENGTH, DEFAULT_HASH_LENGTH, DEFAULT_PARALLELISM, DEFAULT_MEMORY, DEFAULT_ITERATIONS));
        //encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        //encoders.put("scrypt", new SCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(securityProperties.getSecurityEncode().getValue(), encoders);
    }

    @Bean
    public TextEncryptor textEncryptor() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "G@g@n@&K@rth1k@2o21";
        // generate random salt
        SecureRandom random = new SecureRandom();
        byte saltBytes[] = new byte[SALT_BYTE_SIZE]; // use salt size at least as long as hash
        random.nextBytes(saltBytes);
        String salt = password;
        TextEncryptor encryptor = Encryptors.delux(password, new String(Hex.encode(salt.getBytes(StandardCharsets.UTF_8))));
        return encryptor;
    }

    @Bean
    @ConfigurationProperties(prefix = "application.database.r2dbc")
    public R2dbcDatabaseProperties r2dbcDatabaseProperties() {
        return new R2dbcDatabaseProperties();
    }

    @Primary
    @Bean("flywayProperties")
    @ConfigurationProperties(prefix = "application.migration.flyway")
    public FlywayProperties flywayProperties() {
        return new FlywayProperties();
    }
/*
    @Bean
    public AbstractJackson2Decoder jacksonDecoder() {
        return new Jackson2JsonDecoder();
    }
*/
    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    @ConfigurationProperties(prefix = "application.error-properties")
    public ErrorProperties errorProperties() {
        return new ErrorProperties();
    }

    /*
    @Bean
    public ServerCodecConfigurer serverCodecConfigurer() {
        return new DefaultServerCodecConfigurer();
    }
     */

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
        //objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return objectMapper;
    }

    /*

    @Bean
    public Jackson2JsonEncoder jackson2JsonEncoder(ObjectMapper objectMapper) {
        return new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON, MediaType.APPLICATION_NDJSON);
    }

    @Bean
    public Jackson2JsonDecoder jackson2JsonDecoder(ObjectMapper objectMapper) {
        return new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON, MediaType.APPLICATION_NDJSON);
    }

    @Bean
    public Jackson2ObjectMapperBuilder configureObjectMapper(ObjectMapper objectMapper) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.configure(objectMapper);
        return builder;
    }
     */

    @Bean // Makes ZonedDateTime compatible with auditing fields
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(ZonedDateTime.now());
    }

    @Bean
    @Primary
    public DateTimeFormatter dateTimeFormatter() {
        return new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendOptional(DateTimeFormatter.ofPattern("'T'"))
                .appendOptional(DateTimeFormatter.ISO_LOCAL_TIME)
                .appendOptional(DateTimeFormatter.ofPattern("XXX"))
                .toFormatter();

    }

    @Bean("instantDateTimeFormatter")
    public DateTimeFormatter instantDateTimeFormatter() {
        return new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC))
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

    }

    @Bean
    public Manifest manifest() {
        Manifest manifest = null;
        try(InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JarFile.MANIFEST_NAME)) {
            manifest = new Manifest(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return manifest;
    }
}
