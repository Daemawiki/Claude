package com.daemawiki.external.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import org.springframework.security.web.server.header.XXssProtectionServerHttpHeadersWriter;

@Configuration
@RequiredArgsConstructor
class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    @Bean
    SecurityWebFilterChain filterChain(final ServerHttpSecurity http) {
        return http.formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorizeExchange -> authorizeExchange
                        .pathMatchers("/api/auth/**", "/api/mail/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/test").permitAll()
                        .anyExchange().authenticated())
                .headers(headers -> headers
                        .frameOptions(spec -> spec.mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN))
                        .xssProtection(spec -> spec.headerValue(XXssProtectionServerHttpHeadersWriter.HeaderValue.ENABLED_MODE_BLOCK))
                )
                .addFilterBefore(securityFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

}
