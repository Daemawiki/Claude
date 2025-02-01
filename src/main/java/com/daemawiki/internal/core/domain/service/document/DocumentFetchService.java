package com.daemawiki.internal.core.domain.service.document;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.domain.model.primitive.shard.paging.PageNumber;
import com.daemawiki.internal.core.domain.model.primitive.shard.paging.SizeNumber;
import com.daemawiki.internal.core.usecase.document.DocumentFetchUseCase;
import com.daemawiki.internal.core.domain.model.value.shard.paging.PagingRequest;
import com.daemawiki.internal.core.domain.model.value.shard.search.SearchResponse;
import com.daemawiki.internal.data.repository.document.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class DocumentFetchService implements DocumentFetchUseCase {

    private final DocumentRepository documentRepository;

    @Override
    @Cacheable(cacheNames = "documents", key = "#documentId.documentId")
    public Mono<DocumentInternalDTO> fetchById(final DocumentId documentId) {
        return documentRepository.findById(documentId)
                        .switchIfEmpty(Mono.error(CustomExceptionFactory.notFound("문서를 찾지 못했습니다.")));
    }

    @Override
    public Mono<DocumentInternalDTO> fetchRandom() {
        return documentRepository.getRandom();
    }

    // TODO: 1/28/25 검색 캐싱에서 size를 캐싱 키로 두지 않고 서버에서 고정하는 방법 고려
    @Override
    @Cacheable(cacheNames = "documents", key = "{#searchText, #pagingRequest.pageNumber().value(), #pagingRequest.sizeNumber().value()}")
    public Mono<SearchResponse<DocumentInternalDTO>> search(
            final String searchText,
            final PagingRequest pagingRequest
    ) {
        return documentRepository.search(searchText, pagingRequest)
                .collectList()
                .map(result -> createSearchResponse(result, pagingRequest.pageNumber(), pagingRequest.sizeNumber()));
    }

    private SearchResponse<DocumentInternalDTO> createSearchResponse(
            final List<DocumentInternalDTO> result,
            final PageNumber pageNumber,
            final SizeNumber sizeNumber
    ) {
        final int totalElements = result.size();
        final boolean hasNext = totalElements > sizeNumber.value();

        List<DocumentInternalDTO> finalResult;
        if (hasNext) {
            finalResult = result.subList(0, totalElements - 1);
        } else {
            finalResult = result;
        }

        return SearchResponse.create(finalResult, SizeNumber.create(finalResult.size()), pageNumber, hasNext);
    }

}
