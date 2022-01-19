package com.soydasm.taskmanagement.service.impl;



import com.soydasm.taskmanagement.model.User;
import com.soydasm.taskmanagement.service.AuthenticationFacade;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * {@inheritDoc}
 */
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    /**
     * {@inheritDoc}
     */
    @Override
    public User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return getUserFrom(authentication).orElseThrow(() -> new IllegalArgumentException("No user found"));
    }

    private Optional<User> getUserFrom(Authentication authentication) {

        return Optional.ofNullable(((User) authentication.getPrincipal()));

    }
}
