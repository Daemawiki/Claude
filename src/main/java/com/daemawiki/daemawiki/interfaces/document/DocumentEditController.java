package com.daemawiki.daemawiki.interfaces.document;

import com.daemawiki.daemawiki.application.document.usecase.DocumentEditUseCase;
import com.daemawiki.daemawiki.common.annotation.ui.DocumentApi;
import com.daemawiki.daemawiki.common.util.http.ListRequest;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@DocumentApi
@RequiredArgsConstructor
class DocumentEditController {

    private final DocumentEditUseCase editUseCase;

    @PatchMapping("/{documentId}/editor")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> editEditors(
            @PathVariable String documentId,
            @RequestBody ListRequest<DocumentElementDtos.Editor> request
    ) {
        return editUseCase.editEditors(documentId, request.list());
    }

    @PatchMapping("/{documentId}/info")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> editInfo(
            @PathVariable String documentId,
            @RequestBody DocumentElementDtos.UpdateInfo request
    ) {
        return editUseCase.editInfo(documentId, request);
    }
}
