package com.gagana.hospital.api.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse implements Serializable {

    private static final long serialVersionUID = -7612377227404462820L;
    private Long userId;
    private String username;
    private String token;
    private OffsetDateTime issuedAt;
    private OffsetDateTime expiresAt;
}
