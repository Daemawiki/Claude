package com.daemawiki.external.security.paseto;

import com.daemawiki.internal.core.domain.model.primitive.auth.Token;
import reactor.core.publisher.Mono;

public interface Tokenizer {

    Mono<Token> generate(String subject);

}
