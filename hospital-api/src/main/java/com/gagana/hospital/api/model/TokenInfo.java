package com.gagana.hospital.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.OffsetDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {
    private String username;
    private String token;
    private Instant issuedAt;
    private Instant expiresAt;
    private String refreshToken;
    private Instant refreshTokenExpiryAt;
}