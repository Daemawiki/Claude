package com.daemawiki.external.web.rest.document;

import com.daemawiki.external.web.rest.document.dto.form.DocumentEditEditorSetForm;
import com.daemawiki.external.web.rest.document.dto.form.DocumentEditTitleForm;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.usecase.document.DocumentEditUseCase;
import com.daemawiki.archive.daemawiki.common.annotation.ui.DocumentsRestApi;
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
