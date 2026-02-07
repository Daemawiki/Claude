package com.daemawiki.internal.document;

import com.daemawiki.internal.document.dto.DocumentInternalDTO;
import reactor.core.publisher.Mono;

public interface DocumentCreateUseCase {

    Mono<Void> create(DocumentInternalDTO documentDto);

}
