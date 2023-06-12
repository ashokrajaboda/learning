package com.gagana.hospital.api.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gagana.hospital.api.config.helper.LoggingFilterConfig;
import com.gagana.hospital.api.config.properties.SecurityProperties;
import com.gagana.hospital.api.model.CurrentUser;
import com.gagana.hospital.api.model.TokenInfo;
import com.gagana.hospital.api.view.LoginRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthJwtTokenProvider implements ITokenProvider {

    public final SecurityProperties securityProperties;
    public final TextEncryptor textEncryptor;

    public AuthJwtTokenProvider(final SecurityProperties securityProperties,final TextEncryptor textEncryptor) {
        this.securityProperties = securityProperties;
        this.textEncryptor = textEncryptor;
    }

    public String accessToken(String username, List<String> roles, Map<String,Object> headerClaims, String... audiences ) {
        return generate(username, Duration.ofMinutes(10), roles, headerClaims, securityProperties.getTokenEncryptor().salt());
    }

    public String accessToken(String username, Integer expirationInMillis, List<String> roles, Map<String,Object> headerClaims, String... audiences) {
        return generate(username, Duration.ofMillis(expirationInMillis), roles, headerClaims, securityProperties.getTokenEncryptor().salt());
    }

    public DecodedJWT decodeAccessToken(String accessToken) {
        return decode(securityProperties.getTokenEncryptor().salt(), accessToken);
    }

    public String refreshToken(String username, List<String> roles,Map<String,Object> headerClaims, String... audiences) {
        return generate(username, Duration.ofMinutes(10), roles, headerClaims, securityProperties.getTokenRefresh());
    }

    public String refreshToken(String username, Integer expirationInMillis, List<String> roles, Map<String,Object> headerClaims, String... audiences) {
        return generate(username, Duration.ofMillis(expirationInMillis), roles, headerClaims, securityProperties.getTokenRefresh());
    }

    public DecodedJWT decodeRefreshToken(String refreshToken)  {
        return decode(securityProperties.getTokenRefresh(), refreshToken);
    }


    private String generate(String username, TemporalAmount temporalAmount, List<String> roles, Map<String,Object> headerClaims, String signature) {
        return generateToken(username,roles,headerClaims).getToken();
    }

    private DecodedJWT decode(String signature, String token) {
        return JWT.require(Algorithm.HMAC512(signature))
                .build()
                .verify(token.replace("Bearer ", ""));
    }

    public List<SimpleGrantedAuthority> getRoles(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("role").asList(String.class).stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> buildHeaderClaims(CurrentUser currentUser) {
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put(HEADER_CLAIM_USER_ID_KEY,currentUser.getUserId());
        if(StringUtils.isNotBlank(currentUser.getPassword())) {
            headerClaims.put(HEADER_CLAIM_TEXT_ENCRYPTED_PWD_KEY, textEncryptor.encrypt(currentUser.getPassword()));
        }
        if(StringUtils.isNotBlank(currentUser.getSessionId())) {
            headerClaims.put(LoggingFilterConfig.APP_SESSION_ID, textEncryptor.encrypt(currentUser.getSessionId()));
        }
        return headerClaims;
    }

    @Override
    public String getDecryptedValue(String encrypted) {
        return textEncryptor.decrypt(encrypted);
    }

    @Override
    public TokenInfo generateToken(String username, List<String> roles, Map<String, Object> headerClaims,String... audiences) {
        Instant issuedAt = Instant.now();
        TemporalAmount tokenExpiryAmount = Duration.ofMinutes(10);
        TemporalAmount refreshTokenExpiryAmount = Duration.ofHours(2);
        Instant expiresAt = issuedAt.plus(tokenExpiryAmount);
        Instant refreshTokenExpiresAt = issuedAt.plus(refreshTokenExpiryAmount);
        String token = JWT.create()
                .withIssuedAt(Date.from(issuedAt))
                .withHeader(headerClaims)
                .withSubject(username)
                .withExpiresAt(Date.from(expiresAt))
                .withArrayClaim("role", roles.toArray(String[]::new))
                .sign(Algorithm.HMAC512(securityProperties.getTokenEncryptor().salt()));
        String refreshToken = JWT.create()
                .withIssuedAt(Date.from(issuedAt))
                .withHeader(headerClaims)
                .withSubject(username)
                .withExpiresAt(Date.from(refreshTokenExpiresAt))
                .withArrayClaim("role", roles.toArray(String[]::new))
                .sign(Algorithm.HMAC512(securityProperties.getTokenRefresh()));
        return TokenInfo.builder().token(token)
                .refreshToken(refreshToken)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .refreshTokenExpiryAt(refreshTokenExpiresAt)
                .username(username)
                .build();
    }

}
