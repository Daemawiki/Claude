package com.daemawiki.external.web.rest.document;

import com.daemawiki.internal.core.usecase.document.DocumentCreateUseCase;
import com.daemawiki.archive.daemawiki.common.annotation.ui.DocumentsRestApi;
import com.daemawiki.external.web.rest.document.dto.DocumentCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@DocumentsRestApi
@RequiredArgsConstructor
class DocumentCreateController {

    private final DocumentCreateUseCase createUseCase;

    @PostMapping
    Mono<Void> create(
            @RequestBody final DocumentCreateForm form
    ) {
        return createUseCase.create(dto);
    }

}
