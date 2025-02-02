package com.daemawiki.external.web.rest.document;

import com.daemawiki.external.web.rest.document.dto.DocumentCreateForm;
import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
            source = "documentTitle"
    )
    @Mapping(
            target = "categoryList",
            source = "categoryList"
    )
    @Mapping(
            target = "type",
            source = "type"
    )
    DocumentInternalDTO toDocumentDTO(DocumentCreateForm source);

}
