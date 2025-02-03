package com.daemawiki.internal.data.repository.document;

import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
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
    public Mono<DocumentInternalDTO> save(DocumentInternalDTO dto) {
        final var entity = documentEntityMapper.toEntity(dto);

        return documentMongoRepository.save(entity)
                .map(documentEntityMapper::toDTO);
    }

    @Override
    public Mono<DocumentInternalDTO> findById(DocumentId id) {
        return documentMongoRepository.findById(id.documentId())
                .map(documentEntityMapper::toDTO);
    }

    @Override
    public Mono<DocumentInternalDTO> getRandom() {
        return documentMongoRepository.getRandomDocument()
                .map(documentEntityMapper::toDTO);
    }

    @Override
    public Mono<Void> deleteById(DocumentId id) {
        return documentMongoRepository.deleteById(id.documentId());
    }

    @Override
    public Flux<DocumentInternalDTO> search(
            final String searchText,
            final PagingRequest pagingRequest
    ) {
        return documentMongoRepository.search(
                searchText,
                pagingRequest.sortProperty().value(), // TODO: 1/29/25 정렬되는 경로를 매핑해줘야함 ex) input: CREATED, mapping: editedDateTime.createdDateTime
                pagingRequest.sortDirection().value(),
                pagingRequest.pageNumber().value() * pagingRequest.sizeNumber().value(),
                pagingRequest.sizeNumber().value()
        ).map(documentEntityMapper::toDTO);
    }

}
