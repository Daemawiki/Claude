package com.daemawiki.external.security.paseto;

import com.daemawiki.internal.user.primitive.Token;
import reactor.core.publisher.Mono;

public interface Tokenizer {

    Mono<Token> generate(String subject);

}
