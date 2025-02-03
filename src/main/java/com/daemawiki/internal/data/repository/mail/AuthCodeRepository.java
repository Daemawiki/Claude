package com.daemawiki.internal.data.repository.mail;

import com.daemawiki.internal.core.domain.model.dto.mail.AuthCodeDTO;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import reactor.core.publisher.Mono;

public interface AuthCodeRepository {

    Mono<Boolean> save(AuthCodeDTO model);

    Mono<AuthCodeDTO> findByMail(Email email);

    Mono<Long> deleteByEmail(Email email);

}
