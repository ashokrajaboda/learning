package com.gagana.hospital.api.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SecurityProperties {
    private EncryptorProperties encryptor;
    private SecurityEncode securityEncode = SecurityEncode.ARGON2;
    private Integer saltLength;
    private EncryptorProperties tokenEncryptor;
    private String tokenRefresh;
    private Duration tokenExpiry;
    private Duration refreshTokenExpiry;

    private List<String> authWhiteList;

}
