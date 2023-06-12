package com.gagana.hospital.api.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class PoolingDatabaseProperties {
    Integer initialSize;
    Integer maxSize;
    @DurationUnit(ChronoUnit.MINUTES)
    Duration maxIdleTimeInMinutes;
}
