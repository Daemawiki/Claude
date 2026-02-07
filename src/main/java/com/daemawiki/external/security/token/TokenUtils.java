package com.daemawiki.external.security.token;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface TokenUtils {

    Mono<Boolean> validateToken(String token);

    Mono<String> extractUsername(String token);

    Mono<Authentication> getAuthentication(String token);

}
