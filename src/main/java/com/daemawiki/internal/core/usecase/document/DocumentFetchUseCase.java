package com.daemawiki.internal.core.usecase.document;

import com.daemawiki.internal.core.domain.model.value.shard.paging.PagingRequest;
import com.daemawiki.internal.core.domain.model.value.shard.search.SearchResponse;
import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import reactor.core.publisher.Mono;

public interface DocumentFetchUseCase {

    Mono<DocumentInternalDTO> fetchById(DocumentId documentId);

    Mono<DocumentInternalDTO> fetchRandom();

    Mono<SearchResponse<DocumentInternalDTO>> search(
            String searchText,
            PagingRequest pagingRequest
    );

}
