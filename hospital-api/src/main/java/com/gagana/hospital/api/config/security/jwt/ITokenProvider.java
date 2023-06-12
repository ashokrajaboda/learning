package com.gagana.hospital.api.config.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.gagana.hospital.api.model.CurrentUser;
import com.gagana.hospital.api.model.TokenInfo;
import com.gagana.hospital.api.view.LoginRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Map;

public interface ITokenProvider {
    String HEADER_CLAIM_USER_ID_KEY="userId";
    String HEADER_CLAIM_TEXT_ENCRYPTED_PWD_KEY="textEncryptedPwd";
    String accessToken(String username, List<String> roles, Map<String,Object> headerClaims, String... audiences);
    String accessToken(String username, Integer expirationInMillis, List<String> roles,Map<String,Object> headerClaims, String... audiences);
    DecodedJWT decodeAccessToken(String accessToken);

    String refreshToken(String username, List<String> roles, Map<String,Object> headerClaims, String... audiences);
    String refreshToken(String username, Integer expirationInMillis, List<String> roles, Map<String,Object> headerClaims, String... audiences);
    DecodedJWT decodeRefreshToken(String refreshToken);

    List<SimpleGrantedAuthority> getRoles(DecodedJWT decodedJWT);

    Map<String,Object> buildHeaderClaims(CurrentUser currentUser);

    String getDecryptedValue(String encrypted);

    TokenInfo generateToken(String username, List<String> roles, Map<String, Object> buildHeaderClaims, String... audiences);
}
