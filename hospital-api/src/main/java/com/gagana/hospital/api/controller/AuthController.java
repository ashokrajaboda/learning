package com.gagana.hospital.api.controller;

import com.gagana.hospital.api.domain.User;
import com.gagana.hospital.api.model.TokenInfo;
import com.gagana.hospital.api.service.ISecurityService;
import com.gagana.hospital.api.service.UserService;
import com.gagana.hospital.api.view.LoginRequest;
import com.gagana.hospital.api.view.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@RestController
@RequestMapping(value = "/authorize")
public class AuthController {
    private final ISecurityService securityService;

    public AuthController(ISecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping(value = "/token")
    //@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    /*@ApiOperation(value= "Generate Token", response=TokenInfo.class)
    @ApiResponses({
        @ApiResponse(code = 200, message= "Generated Token Successfully"),
        @ApiResponse(code = 401, message= "UNAUTHORIZED"), 
        @ApiResponse(code = 500, message= "Internal technical error") })
     */
    public Mono<ResponseEntity<TokenInfo>> authorizeToken(@Validated  @RequestBody LoginRequest loginRequest) {

        return securityService.authenticate(loginRequest)
                .flatMap(tokenInfo -> Mono.just(ResponseEntity.ok(tokenInfo)));
    }

}
