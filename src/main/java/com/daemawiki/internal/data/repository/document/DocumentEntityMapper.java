package com.daemawiki.internal.data.repository.document;

import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.ERROR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(
        componentModel = SPRING,
        unmappedTargetPolicy = ERROR,
        unmappedSourcePolicy = IGNORE
)
interface DocumentEntityMapper {

    @Mapping(
            target = "id",
            expression = "java(source.documentId().value())",
            ignore = true
    )
    @Mapping(
            target = "title",
            expression = "java(new DocumentEntity.Title("
                        + "source.documentTitle().mainTitle().value(), "
                        + "source.documentTitle().subTitle().value()"
                    + "))"
    )
    @Mapping(
            target = "content",
            expression = "java(source.documentContent().textBody().value())",
            defaultValue = " "
    )
    @Mapping(
            target = "detailMap",
            expression = "java(source.documentContent().detailMap().entrySet().stream().collect(Collectors.toMap("
                        + "e -> e.getKey().value(), "
                        + "e -> e.getValue().value()"
                    + ")))",
            defaultExpression = "java(Map.of())"
    )
    @Mapping(
            target = "categoryList",
            expression = "java(source.categoryList().stream()" +
                    ".map(e -> e.category()).toList())",
            defaultExpression = "java(new LinkedList<>())"
    )
    @Mapping(
            target = "viewCount",
            expression = "java(source.documentInfo().viewCount().value())",
            defaultValue = "0"
    )
    @Mapping(
            target = "version",
            expression = "java(source.documentInfo().version().value())",
            defaultValue = "0"
    )
    @Mapping(
            target = "type",
            expression = "java(source.type().name())"
    )
    @Mapping(
            target = "editedDateTime",
            expression = "java(DocumentEntity.EditedDateTime.create("
                        + "source.editedDateTime().createdDateTime().value(), "
                        + "source.editedDateTime().lastModifiedDateTime().value()"
                    + "))",
            defaultExpression = "java(EditedDateTime.create())"
    )
    @Mapping(
            target = "owner",
            expression = "java(DocumentEntity.Editor.create("
                        + "source.documentEditor().owner().name(), "
                        + "source.documentEditor().owner().userId()"
                    + "))"
    )
    @Mapping(
            target = "editorSet",
            expression = "java(source.documentEditor().editorSet().stream().map("
                        + "e -> DocumentEntity.Editor.create(e.name(), e.userId())"
                    + ").collect(Collectors.toSet()))",
            defaultExpression = "java(new HashSet<>())"
    )
    DocumentEntity toEntity(DocumentInternalDTO source);

    @Mapping(
            target = "documentId",
            expression = "java(DocumentId.create(source.getId()))"
    )
    @Mapping(
            target = "documentTitle",
            expression = "java(DocumentTitle.create("
                    + "MainTitle.create(source.getTitle().mainTitle()), "
                    + "SubTitle.create(source.getTitle().subTitle())"
                    + "))"
    )
    @Mapping(
            target = "documentContent",
            expression = "java(DocumentContent.create("
                    + "TextBody.create(source.getContent()), "
                    + "source.getDetailMap().entrySet().stream().collect(Collectors.toMap("
                    + "e -> DetailKey.create(e.getKey()), "
                    + "e -> Description.create(e.getValue())"
                    + "))))"
    )
    @Mapping(
            target = "categoryList",
            expression = "java(source.getCategoryList().stream()" +
                    ".map(e -> DocumentCategory.create(e))" +
                    ".toList())"
    )
    @Mapping(
            target = "documentInfo",
            expression = "java(DocumentInfo.create("
                    + "ViewCount.create(source.getViewCount()), "
                    + "Version.create(source.getVersion())"
                    + "))"
    )
    @Mapping(
            target = "type",
            expression = "java(DocumentType.valueOf(source.getType()))"
    )
    @Mapping(
            target = "editedDateTime",
            expression = "java(EditedDateTime.create("
                    + "CreatedDateTime.create(source.getEditedDateTime().createdDateTime()), "
                    + "LastModifiedDateTime.create(source.getEditedDateTime().lastModifiedDateTime())"
                    + "))"
    )
    @Mapping(
            target = "documentEditor",
            expression = "java(DocumentEditor.create("
                    + "Editor.create(source.getOwner().name(), source.getOwner().userId()), "
                    + "source.getEditorSet().stream().map(e -> Editor.create(e.name(), e.userId())).collect(Collectors.toSet())"
                    + "))"
    )
    DocumentInternalDTO toDTO(DocumentEntity source);

}
