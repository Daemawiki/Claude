package com.daemawiki.internal.core.domain.model.dto.document;

import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentCategory;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentType;
import com.daemawiki.internal.core.domain.model.primitive.document.content.TextBody;
import com.daemawiki.internal.core.domain.model.primitive.document.detail.Description;
import com.daemawiki.internal.core.domain.model.primitive.document.detail.DetailKey;
import com.daemawiki.internal.core.domain.model.primitive.document.title.MainTitle;
import com.daemawiki.internal.core.domain.model.primitive.document.title.SubTitle;
import com.daemawiki.internal.core.domain.model.primitive.user.UserId;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Generation;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Major;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Name;
import com.daemawiki.internal.core.domain.model.value.document.DocumentContent;
import com.daemawiki.internal.core.domain.model.value.document.DocumentEditor;
import com.daemawiki.internal.core.domain.model.value.document.DocumentInfo;
import com.daemawiki.internal.core.domain.model.value.document.DocumentTitle;
import com.daemawiki.internal.core.domain.model.value.shard.date.EditedDateTime;
import com.daemawiki.internal.core.domain.model.value.user.Editor;
import lombok.Builder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Builder
public record DocumentInternalDTO(
        DocumentId documentId,
        DocumentTitle documentTitle,
        DocumentContent documentContent,
        List<DocumentCategory> categoryList,
        DocumentInfo documentInfo,
        DocumentType type,
        EditedDateTime editedDateTime,
        DocumentEditor documentEditor
) {

    public DocumentInternalDTO updateTitle(final DocumentTitle documentTitle) {
        return new DocumentInternalDTO(documentId, documentTitle, documentContent, categoryList, documentInfo, type, editedDateTime, documentEditor);
    }

    public DocumentInternalDTO updateContent(final DocumentContent documentContent) {
        return new DocumentInternalDTO(documentId, documentTitle, documentContent, categoryList, documentInfo, type, editedDateTime, documentEditor);
    }

    public DocumentInternalDTO updateEditorSet(final Set<Editor> editorSet) {
        return new DocumentInternalDTO(documentId, documentTitle, documentContent, categoryList, documentInfo, type, editedDateTime, documentEditor.updateEditorSet(editorSet));
    }

    public boolean isOwner(final UserId userId) {
        return documentEditor()
                .owner()
                .userId()
                .equals(userId);
    }

    public boolean isEditor(final UserId userId) {
        return (documentEditor
                    .owner()
                    .userId()
                    .equals(userId)) ||
                (documentEditor
                    .editorSet()
                    .contains(Editor.create(Name.emptyInstance(), userId)));
    }

    public DocumentInternalDTO updateOwner(final UserInternalDTO userDto) {
        return DocumentInternalDTO.builder()
                .documentId(documentId)
                .documentTitle(documentTitle)
                .documentContent(documentContent)
                .categoryList(categoryList)
                .documentInfo(documentInfo)
                .type(type)
                .editedDateTime(editedDateTime)
                .documentEditor(
                        DocumentEditor.create(
                            Editor.create(
                                    userDto.personalData().name(),
                                    userDto.userId()
                            ),
                            documentEditor.editorSet()
                        )
                )
                .build();
    }

    public static DocumentInternalDTO createByUserDto(final UserInternalDTO userDto) {
        final Generation generation = userDto.personalData().generation();
        final Major major = userDto.personalData().major();

        final Name name = userDto.personalData().name();

        final String generationString = generation.generation()  + "기";
        final String majorString = major.major();

        return DocumentInternalDTO.builder()
                .documentTitle(
                        DocumentTitle.create(
                                MainTitle.create(name.name()),
                                SubTitle.create("학생")
                        )
                )
                .documentContent(
                        DocumentContent.create(
                                TextBody.createEmpty(),
                                Map.of(
                                        DetailKey.create("기수"), Description.create(generationString),
                                        DetailKey.create("전공"), Description.create(majorString)
                                )
                        )
                )
                .categoryList(
                        List.of(
                                DocumentCategory.create(generationString),
                                DocumentCategory.create(majorString)
                        )
                )
                .type(DocumentType.STUDENT)
                .documentEditor(
                        DocumentEditor.create(
                            Editor.create(
                                    name,
                                    userDto.userId()
                            ),
                            new HashSet<>()
                        )
                )
                .build();
    }

}
