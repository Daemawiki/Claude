package com.daemawiki.internal.document.service;

import com.daemawiki.internal.document.dto.DocumentInternalDTO;
import com.daemawiki.internal.document.DocumentCreateUseCase;
import com.daemawiki.internal.core.domain.component.auth.CurrentUser;
import com.daemawiki.internal.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class DocumentCreateService implements DocumentCreateUseCase {

    private final DocumentRepository documentRepository;
    private final CurrentUser currentUser;

    @Override
    @Transactional
    public Mono<Void> create(final DocumentInternalDTO documentDto) {
        return currentUser.get()
                .map(documentDto::updateOwner)
                .flatMap(documentRepository::save)
                .then();
    }

}
