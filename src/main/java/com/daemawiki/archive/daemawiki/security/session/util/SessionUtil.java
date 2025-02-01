package com.daemawiki.archive.daemawiki.security.session.util;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface SessionUtil {
    Mono<Authentication> getAuthentication(String subject);
}
