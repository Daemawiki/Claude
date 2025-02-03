package com.daemawiki.external.web.rest.document;

import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.usecase.document.DocumentFetchUseCase;
import com.daemawiki.archive.daemawiki.common.annotation.ui.DocumentsRestApi;
import com.daemawiki.internal.core.domain.model.value.shard.paging.PagingRequest;
import com.daemawiki.internal.core.domain.model.value.shard.search.SearchResponse;
import com.daemawiki.external.web.rest.document.dto.DocumentHalfResponse;
import com.daemawiki.external.web.rest.document.dto.DocumentFullResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@DocumentsRestApi
@RequiredArgsConstructor
class DocumentFetchController {

    private final DocumentFetchUseCase documentFetchUseCase;

    private final DocumentDTOMapper documentDTOMapper;

    @GetMapping("/random")
    Mono<DocumentFullResponse> fetchRandom() {
        return documentFetchUseCase.fetchRandom()
                .map(documentDTOMapper::toDocumentFullResponse);
    }

    @GetMapping("/search")
    Mono<SearchResponse<DocumentHalfResponse>> search(
            @RequestParam final String text,
            @ModelAttribute final PagingRequest request // TODO: 2/3/25 계층 분리
    ) {
        return documentFetchUseCase.search(text, request)
                .map(e -> SearchResponse.create(
                        documentDTOMapper.toDocumentHalfResponseList(e.data()),
                        e.sizeNumber(),
                        e.pageNumber(),
                        e.hasNextPage()
                ));
    }

    @GetMapping("/{documentId}")
    Mono<DocumentFullResponse> fetchById(
            @PathVariable final DocumentId documentId
    ) {
        return documentFetchUseCase.fetchById(documentId)
                .map(documentDTOMapper::toDocumentFullResponse);
    }

}
