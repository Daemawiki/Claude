package com.daemawiki.internal.mail.repository;

import com.daemawiki.internal.mail.dto.AuthCodeDTO;
import com.daemawiki.internal.user.primitive.personal.Email;
import reactor.core.publisher.Mono;

public interface AuthCodeRepository {

    Mono<Boolean> save(AuthCodeDTO model);

    Mono<AuthCodeDTO> findByMail(Email email);

    Mono<Long> deleteByEmail(Email email);

}
