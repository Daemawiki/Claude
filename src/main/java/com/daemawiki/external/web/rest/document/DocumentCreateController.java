package com.daemawiki.external.web.rest.document;

import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import com.daemawiki.internal.core.usecase.document.DocumentCreateUseCase;
import com.daemawiki.archive.daemawiki.common.annotation.ui.DocumentsRestApi;
import com.daemawiki.external.web.rest.document.dto.form.DocumentCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@DocumentsRestApi
@RequiredArgsConstructor
class DocumentCreateController {

    private final DocumentCreateUseCase documentCreateUseCase;

    private final DocumentDTOMapper documentDTOMapper;

    @PostMapping
    Mono<Void> create(
            @RequestBody final DocumentCreateForm form
    ) {
        final DocumentInternalDTO dto = documentDTOMapper.toDocumentDTO(form);

        return documentCreateUseCase.create(dto);
    }

}
