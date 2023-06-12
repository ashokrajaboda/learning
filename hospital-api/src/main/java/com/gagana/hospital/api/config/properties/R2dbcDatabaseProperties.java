package com.gagana.hospital.api.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class R2dbcDatabaseProperties {
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration connectTimeoutInSeconds;
    //@NotBlank
    private String database;
    //@NotBlank
    private String driver;
    //@NotBlank
    private String host;

    private Boolean encrypted = Boolean.TRUE;

    private CharSequence password;
    //@NotNull
    private Integer port;

    private String protocol;
    private Boolean ssl;
    //@NotBlank
    private String user;
    private PoolingDatabaseProperties pooling;
    private Map<String, String> options;

}
