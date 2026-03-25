package com.felfel.dealer_vehicle_inventory_module.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
//    private final CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler;
//
//    public SecurityConfiguration(CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler) {
//        this.customBearerTokenAccessDeniedHandler = customBearerTokenAccessDeniedHandler;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth->auth
                        .anyRequest().permitAll()
                ) .headers(
                        header->header
                                .contentSecurityPolicy(csp->csp
                                        .policyDirectives("frame-ancestors 'self'"))
                )
//                .oauth2ResourceServer(oauth2->oauth2
//                        .accessDeniedHandler(customBearerTokenAccessDeniedHandler))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
