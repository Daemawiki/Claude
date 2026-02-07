package com.daemawiki.internal.document;

import com.daemawiki.internal.common.primitive.shard.SearchText;
import com.daemawiki.internal.common.value.shard.PagingRequest;
import com.daemawiki.internal.common.value.shard.SearchResponse;
import com.daemawiki.internal.document.dto.DocumentInternalDTO;
import com.daemawiki.internal.document.primitive.DocumentId;
import reactor.core.publisher.Mono;

public interface DocumentFetchUseCase {

    Mono<DocumentInternalDTO> fetchById(DocumentId documentId);

    Mono<DocumentInternalDTO> fetchRandom();

    Mono<SearchResponse<DocumentInternalDTO>> search(
            SearchText searchText,
            PagingRequest pagingRequest
    );

}
