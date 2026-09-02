package com.example.CRUD_Springboot.config;

import com.example.CRUD_Springboot.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // JWT-based API: CSRF is not required
                .csrf(csrf -> csrf.disable())

                // Enable CORS configuration
                .cors(cors -> {})

                // Disable browser-based authentication
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())

                // Stateless because JWT is used
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                // Standard JSON responses for 401 / 403
                .exceptionHandling(exception -> exception

                        .authenticationEntryPoint((request, response, authException) -> {

                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json");

                            response.getWriter().write("""
                                    {
                                        "status": 401,
                                        "error": "UNAUTHORIZED",
                                        "message": "Authentication is required"
                                    }
                                    """);
                        })

                        .accessDeniedHandler((request, response, accessDeniedException) -> {

                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json");

                            response.getWriter().write("""
                                    {
                                        "status": 403,
                                        "error": "FORBIDDEN",
                                        "message": "You do not have permission to perform this operation"
                                    }
                                    """);
                        })
                )

                // Role-based authorization
                .authorizeHttpRequests(auth -> auth

                        // Authentication endpoints are public
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()

                        // Swagger / OpenAPI
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        )
                        .permitAll()

                        // Product write operations - ADMIN only
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/products"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/products/**"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/products/**"
                        )
                        .hasRole("ADMIN")

                        // Product read operations - ADMIN or USER
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/products/**"
                        )
                        .hasAnyRole("ADMIN", "USER")

                        // Everything else requires authentication
                        .anyRequest()
                        .authenticated()
                )

                // JWT authentication
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:5173"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}