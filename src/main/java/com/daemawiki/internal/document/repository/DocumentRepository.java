package com.daemawiki.internal.document.repository;

import com.daemawiki.internal.document.dto.DocumentInternalDTO;
import com.daemawiki.internal.document.primitive.DocumentId;
import com.daemawiki.internal.core.domain.model.primitive.shard.search.SearchText;
import com.daemawiki.internal.core.domain.model.value.shard.paging.PagingRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DocumentRepository {

    Mono<DocumentInternalDTO> save(DocumentInternalDTO dto);

    Mono<DocumentInternalDTO> findById(DocumentId documentId);

    Mono<DocumentInternalDTO> getRandom();

    Mono<Void> deleteById(DocumentId documentId);

    Flux<DocumentInternalDTO> search(
            SearchText searchText,
            PagingRequest pagingRequest
    );

}
