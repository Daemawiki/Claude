package com.daemawiki.external.security;

import com.daemawiki.external.exception.ExceptionResponse;
import com.daemawiki.external.security.paseto.TokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.paseto.jpaseto.PasetoException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
class SecurityFilter implements WebFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();

    private static final String HANDLE_MESSAGE = "유효하지 않은 토큰입니다.";

    private final ObjectMapper objectMapper;

    private final TokenUtils tokenUtils;

    @Override
    public Mono<Void> filter(
            final ServerWebExchange exchange,
            final WebFilterChain chain
    ) {
        final String token = resolveHeaderToken(exchange.getRequest());

        if (token != null) {
            return tokenUtils.getAuthentication(token)
                    .flatMap(auth -> chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)))
                    .onErrorResume(PasetoException.class, e -> handleSessionException(exchange));
        }

        return chain.filter(exchange);
    }

    private String resolveHeaderToken(final ServerHttpRequest request) {
        final String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX_LENGTH);
        }
        return null;
    }

    private Mono<Void> handleSessionException(final ServerWebExchange exchange) {
        final var errorResponse = ExceptionResponse.ofSecurityError(
                HttpStatus.UNAUTHORIZED,
                HANDLE_MESSAGE
        );

        byte[] responseBytes;
        try {
            responseBytes = objectMapper.writeValueAsBytes(errorResponse);
        } catch (JsonProcessingException jsonProcessingException) {
            responseBytes = errorResponse.toString().getBytes();
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return exchange.getResponse().writeWith(wrapResponseToDataBuffer(exchange, responseBytes));
    }

    private static Mono<DataBuffer> wrapResponseToDataBuffer(
            final ServerWebExchange exchange,
            final byte[] responseBytes
    ) {
        return Mono.just(exchange.getResponse()
                .bufferFactory()
                .wrap(responseBytes));
    }

}
