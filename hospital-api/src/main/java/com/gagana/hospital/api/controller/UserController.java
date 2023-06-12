package com.gagana.hospital.api.controller;

import com.gagana.hospital.api.domain.User;
import com.gagana.hospital.api.model.CurrentUser;
import com.gagana.hospital.api.service.ISecurityService;
import com.gagana.hospital.api.service.UserService;
import com.gagana.hospital.api.view.LoginRequest;
import com.gagana.hospital.api.view.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final ISecurityService securityService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(ISecurityService securityService, UserService userService, PasswordEncoder passwordEncoder) {
        this.securityService = securityService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/signup")
    public Mono<User> signup(@Validated @RequestBody User user) {
        Mono<User> saveUser = userService.saveUser(user);
        return saveUser;
    }

    @GetMapping
    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("permitAll()")
    public Mono<CurrentUser> currentUser(Authentication authentication) {
        var principal = (CurrentUser) authentication.getPrincipal();
        return Mono.just(principal);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/roleAdmin")
    public Mono<String> roleAdmin() {
        var value = "Role Admin Authorize";
        return Mono.just(value);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/admin")
    public Mono<CurrentUser> admin(Authentication authentication) {
        var principal = (CurrentUser) authentication.getPrincipal();
        return Mono.just(principal);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('MEDICAL_ADMIN')")
    @GetMapping(value = "/medicalAdmin")
    public Mono<CurrentUser> medicalAdmin(Authentication authentication) {
        var principal = (CurrentUser) authentication.getPrincipal();
        return Mono.just(principal);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('ROLE)MEDICAL_ADMIN')")
    @GetMapping(value = "/roleMedicalAdmin")
    public Mono<CurrentUser> roleMedicalAdmin(Authentication authentication) {
        var principal = (CurrentUser) authentication.getPrincipal();
        return Mono.just(principal);
    }
}
