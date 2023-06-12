package com.gagana.hospital.api.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CurrentUser extends User {
    private final Long userId;
    private final Set<String> roles;
    private final String sessionId;

    public CurrentUser(Long userId,String username, String password, final String sessionId, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        roles = authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.toUnmodifiableSet());
        this.sessionId = sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public List<String> getRoles() {
        return new ArrayList<>(roles);
    }

    public String getSessionId() {
        return sessionId;
    }
}
