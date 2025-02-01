package com.daemawiki.internal.data.repository.document;

import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.domain.model.value.shard.paging.PagingRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DocumentRepository {

    Mono<DocumentInternalDTO> save(DocumentInternalDTO dto);

    Mono<DocumentInternalDTO> findById(DocumentId id);

    Mono<DocumentInternalDTO> getRandom();

    Mono<Void> deleteById(DocumentId id);

    Flux<DocumentInternalDTO> search(
            String searchText,
            PagingRequest pagingRequest
    );

}
