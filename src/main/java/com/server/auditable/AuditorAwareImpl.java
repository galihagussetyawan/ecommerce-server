package com.server.auditable;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        final String currentUser;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            currentUser = authentication.getName();
        } else {
            currentUser = null;
        }
        return Optional.ofNullable(currentUser);
    }

}
