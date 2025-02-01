package com.daemawiki.external.web.rest.document;

import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.usecase.document.DocumentFetchUseCase;
import com.daemawiki.archive.daemawiki.common.annotation.ui.DocumentsRestApi;
import com.daemawiki.internal.core.domain.model.value.shard.paging.PagingRequest;
import com.daemawiki.internal.core.domain.model.value.shard.search.SearchResponse;
import com.daemawiki.external.web.rest.document.dto.DocumentHalfResponse;
import com.daemawiki.external.web.rest.document.dto.DocumentFullResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@DocumentsRestApi
@RequiredArgsConstructor
class DocumentFetchController {

    private final DocumentFetchUseCase fetchUseCase;

    @GetMapping("/random")
    Mono<DocumentFullResponse> fetchRandom() {
        return fetchUseCase.fetchRandom();
    }

    @GetMapping("/search")
    Mono<SearchResponse<DocumentHalfResponse>> search(
            @RequestParam String text,
            @ModelAttribute @Valid PagingRequest request
    ) {
        return fetchUseCase.search(text, request);
    }

    @GetMapping("/{documentId}")
    Mono<DocumentFullResponse> fetchById(
            @PathVariable DocumentId documentId
    ) {
        return fetchUseCase.fetchById(documentId);
    }

}
