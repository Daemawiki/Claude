package com.daemawiki.external.api.rest.document;

import com.daemawiki.external.api.rest.document.dto.DocumentEditEditorSetForm;
import com.daemawiki.external.api.rest.document.dto.DocumentEditTitleForm;
import com.daemawiki.internal.document.primitive.DocumentId;
import com.daemawiki.internal.document.DocumentEditUseCase;
import com.daemawiki.external.api.annotation.DocumentsRestApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@DocumentsRestApi
@RequiredArgsConstructor
class DocumentEditController {

    private final DocumentEditUseCase documentEditUseCase;

    @PatchMapping("/{documentId}/editors")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> editEditors(
            @PathVariable final DocumentId documentId,
            @RequestBody final DocumentEditEditorSetForm form
    ) {
        return documentEditUseCase.editEditorSet(documentId, form.editorList());
    }

    @PatchMapping("/{documentId}/title")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> editTitle(
            @PathVariable final DocumentId documentId,
            @RequestBody final DocumentEditTitleForm form
    ) {
        return documentEditUseCase.editTitle(documentId, form.documentTitle());
    }

}
