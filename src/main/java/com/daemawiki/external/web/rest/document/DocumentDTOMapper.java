package com.daemawiki.external.web.rest.document;

import com.daemawiki.external.web.rest.document.dto.DocumentFullResponse;
import com.daemawiki.external.web.rest.document.dto.DocumentHalfResponse;
import com.daemawiki.external.web.rest.document.dto.form.DocumentCreateForm;
import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Stream;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(
        componentModel = SPRING,
        unmappedTargetPolicy = IGNORE,
        unmappedSourcePolicy = IGNORE
)
public interface DocumentDTOMapper {

    @Mapping(
            target = "documentTitle",
            source = "documentTitle",
            ignore = false
    )
    @Mapping(
            target = "categoryList",
            source = "categoryList",
            ignore = false
    )
    @Mapping(
            target = "type",
            source = "type",
            ignore = false
    )
    DocumentInternalDTO toDocumentDTO(DocumentCreateForm source);

    DocumentFullResponse toDocumentFullResponse(DocumentInternalDTO source);

    @Mapping(
            target = "mainTitle",
            source = "documentTitle.mainTitle",
            ignore = false
    )
    @Mapping(
            target = "content",
            source = "documentContent.textBody",
            ignore = false
    )
    DocumentHalfResponse toDocumentHalfResponse(DocumentInternalDTO source);

    default List<DocumentHalfResponse> toDocumentHalfResponseList(List<DocumentInternalDTO> source) {
        return source.stream()
                .map(this::toDocumentHalfResponse)
                .toList();
    }

}
