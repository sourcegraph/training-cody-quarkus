package com.sourcegraph.petstore.config;

import io.quarkus.security.spi.runtime.AuthorizationController;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.interceptor.Interceptor;

@Alternative
@Priority(Interceptor.Priority.LIBRARY_AFTER)
@ApplicationScoped
public class SecurityConfig extends AuthorizationController {

    @Override
    public boolean isAuthorizationEnabled() {
        // Disable authorization for this demo app
        return false;
    }
}