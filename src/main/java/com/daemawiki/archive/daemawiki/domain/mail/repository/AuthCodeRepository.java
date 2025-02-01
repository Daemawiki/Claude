package com.daemawiki.archive.daemawiki.domain.mail.repository;

import com.daemawiki.internal.core.domain.model.dto.mail.AuthCodeDTO;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import reactor.core.publisher.Mono;

public interface AuthCodeRepository {

    Mono<Boolean> save(AuthCodeDTO model);

    Mono<AuthCodeDTO> findByMail(Email email);

    Mono<Void> delete(AuthCodeDTO model);

    Mono<Long> deleteByEmail(Email email);

}
