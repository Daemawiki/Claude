package com.daemawiki.internal.data.repository.document;

import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
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
