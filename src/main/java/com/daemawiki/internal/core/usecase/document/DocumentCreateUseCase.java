package com.daemawiki.internal.core.usecase.document;

import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import reactor.core.publisher.Mono;

public interface DocumentCreateUseCase {

    Mono<Void> create(DocumentInternalDTO documentDto);

}
