    package com.felfel.dealer_vehicle_inventory_module.security;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.provisioning.InMemoryUserDetailsManager;
    import org.springframework.security.web.SecurityFilterChain;

    /**
     * Configuration for Spring Security to handle authentication and authorization.
     * Implements role-based access control and stateless session management.
     */
    @Configuration
    public class SecurityConfiguration {
        /**
         * Configures the security filter chain.
         * Restricts '/admin/**' to GLOBAL_ADMIN and requires authentication for all other requests.
         *
         * @param httpSecurity the http security configuration.
         * @return the configured security filter chain.
         * @throws Exception if an error occurs during configuration.
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            return httpSecurity
                    .authorizeHttpRequests(auth->auth
                        .requestMatchers("/admin/**").hasRole("GLOBAL_ADMIN")
                        .anyRequest().authenticated()
                    )
                    .httpBasic(Customizer.withDefaults())
                    .headers(
                            header->header
                                    .contentSecurityPolicy(csp->csp
                                            .policyDirectives("frame-ancestors 'self'"))
                    )
                    .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .csrf(AbstractHttpConfigurer::disable)
                    .build();
        }

        /**
         * Password encoder password encoder.
         *
         * @return the password encoder
         */
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        /**
         * User details service user details service.
         *
         * @return the user details service
         */
        @Bean
        public UserDetailsService userDetailsService() {
            UserDetails admin = User.withUsername("admin")
                    .password(passwordEncoder().encode("admin123"))
                    .roles("GLOBAL_ADMIN")
                    .build();

            UserDetails user = User.withUsername("user")
                    .password(passwordEncoder().encode("user123"))
                    .roles("USER")
                    .build();

            return new InMemoryUserDetailsManager(admin, user);
        }
    }
