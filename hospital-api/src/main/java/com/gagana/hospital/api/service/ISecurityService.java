package com.gagana.hospital.api.service;

import com.gagana.hospital.api.model.TokenInfo;
import com.gagana.hospital.api.view.LoginRequest;
import com.gagana.hospital.api.view.TokenResponse;
import reactor.core.publisher.Mono;

public interface ISecurityService {
    Mono<TokenInfo> authenticate(LoginRequest loginRequest);
}
