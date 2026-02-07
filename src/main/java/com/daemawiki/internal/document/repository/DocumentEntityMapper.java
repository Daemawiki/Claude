package com.daemawiki.internal.document.repository;

import com.daemawiki.internal.document.dto.DocumentInternalDTO;
import com.daemawiki.internal.document.primitive.DocumentCategory;
import com.daemawiki.internal.document.primitive.DocumentId;
import com.daemawiki.internal.document.primitive.DocumentType;
import com.daemawiki.internal.document.primitive.content.TextBody;
import com.daemawiki.internal.document.primitive.detail.Description;
import com.daemawiki.internal.document.primitive.detail.DetailKey;
import com.daemawiki.internal.document.primitive.title.MainTitle;
import com.daemawiki.internal.document.primitive.title.SubTitle;
import com.daemawiki.internal.common.primitive.shard.CreatedDateTime;
import com.daemawiki.internal.common.primitive.shard.LastModifiedDateTime;
import com.daemawiki.internal.common.primitive.shard.Version;
import com.daemawiki.internal.common.primitive.shard.ViewCount;
import com.daemawiki.internal.user.primitive.UserId;
import com.daemawiki.internal.user.primitive.personal.Name;
import com.daemawiki.internal.document.vo.DocumentContent;
import com.daemawiki.internal.document.vo.DocumentEditor;
import com.daemawiki.internal.document.vo.DocumentInfo;
import com.daemawiki.internal.document.vo.DocumentTitle;
import com.daemawiki.internal.common.value.shard.EditedDateTime;
import com.daemawiki.internal.user.vo.Editor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.ERROR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(
        componentModel = SPRING,
        unmappedTargetPolicy = ERROR,
        unmappedSourcePolicy = IGNORE,
        imports = {
                Collectors.class,
                LocalDateTime.class,
                DocumentTitle.class,
                MainTitle.class,
                SubTitle.class,
                DocumentCategory.class,
                DocumentId.class,
                DocumentType.class,
                Description.class,
                DetailKey.class,
                TextBody.class,
                DocumentContent.class,
                DocumentEditor.class,
                DocumentInfo.class,
                ViewCount.class,
                Version.class,
                Editor.class,
                UserId.class,
                Name.class,
                EditedDateTime.class,
                CreatedDateTime.class,
                LastModifiedDateTime.class
        }
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
            expression = "java(source.documentContent().textBody().value())"
    )
    @Mapping(
            target = "detailMap",
            expression = "java(source.documentContent().detailMap().entrySet().stream().collect(Collectors.toMap("
                        + "e -> e.getKey().value(), "
                        + "e -> e.getValue().value()"
                    + ")))"
    )
    @Mapping(
            target = "categoryList",
            expression = "java(source.categoryList().stream()" +
                    ".map(e -> e.value()).toList())"
    )
    @Mapping(
            target = "viewCount",
            expression = "java(source.documentInfo().viewCount().value())"
    )
    @Mapping(
            target = "version",
            expression = "java(source.documentInfo().version().value())"
    )
    @Mapping(
            target = "type",
            expression = "java(source.type().name())"
    )
    @Mapping(
            target = "editedDateTime",
            expression = "java(DocumentEntity.EditedDateTime.create("
                        + "LocalDateTime.parse(source.editedDateTime().createdDateTime().value()),"
                        + "LocalDateTime.parse(source.editedDateTime().lastModifiedDateTime().value())"
                    + "))"
    )
    @Mapping(
            target = "owner",
            expression = "java(DocumentEntity.Editor.create("
                        + "source.documentEditor().owner().name().value(), "
                        + "source.documentEditor().owner().userId().value()"
                    + "))"
    )
    @Mapping(
            target = "editorSet",
            expression = "java(source.documentEditor().editorSet().stream().map("
                        + "e -> DocumentEntity.Editor.create(e.name().value(), e.userId().value())"
                    + ").collect(Collectors.toSet()))"
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
                    + "Editor.create(Name.create(source.getOwner().name()), UserId.create(source.getOwner().userId())), "
                    + "source.getEditorSet().stream().map(e -> Editor.create(Name.create(e.name()), UserId.create(e.userId()))).collect(Collectors.toSet())"
                    + "))"
    )
    DocumentInternalDTO toDTO(DocumentEntity source);

}
