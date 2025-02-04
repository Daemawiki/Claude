package com.daemawiki.internal.data.repository.document;

import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.domain.model.primitive.shard.search.SearchText;
import com.daemawiki.internal.core.domain.model.value.shard.paging.PagingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
class DocumentRepositoryImpl implements DocumentRepository {

    private final DocumentMongoRepository documentMongoRepository;
    private final DocumentEntityMapper documentEntityMapper;

    @Override
    public Mono<DocumentInternalDTO> save(final DocumentInternalDTO dto) {
        final var entity = documentEntityMapper.toEntity(dto);

        return documentMongoRepository.save(entity)
                .map(documentEntityMapper::toDTO);
    }

    @Override
    public Mono<DocumentInternalDTO> findById(final DocumentId documentId) {
        return documentMongoRepository.findById(documentId.value())
                .map(documentEntityMapper::toDTO);
    }

    @Override
    public Mono<DocumentInternalDTO> getRandom() {
        return documentMongoRepository.getRandomDocument()
                .map(documentEntityMapper::toDTO);
    }

    @Override
    public Mono<Void> deleteById(final DocumentId documentId) {
        return documentMongoRepository.deleteById(documentId.value());
    }

    @Override
    public Flux<DocumentInternalDTO> search(
            final SearchText searchText,
            final PagingRequest pagingRequest
    ) {
        return documentMongoRepository.search(
                searchText.value(),
                pagingRequest.sortProperty().value(), // TODO: 1/29/25 정렬되는 경로를 매핑해줘야함 ex) input: CREATED, mapping: editedDateTime.createdDateTime
                pagingRequest.sortDirection().value(),
                pagingRequest.pageNumber().value() * pagingRequest.sizeNumber().value(),
                pagingRequest.sizeNumber().value()
        ).map(documentEntityMapper::toDTO);
    }

}
