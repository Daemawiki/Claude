package com.daemawiki.external.web.rest.document.dto;

import com.daemawiki.internal.core.domain.model.primitive.document.DocumentCategory;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentType;
import com.daemawiki.internal.core.domain.model.value.document.DocumentContent;
import com.daemawiki.internal.core.domain.model.value.document.DocumentEditor;
import com.daemawiki.internal.core.domain.model.value.document.DocumentInfo;
import com.daemawiki.internal.core.domain.model.value.document.DocumentTitle;
import com.daemawiki.internal.core.domain.model.value.shard.date.EditedDateTime;

import java.util.List;

public record DocumentFullResponse(
        DocumentId documentId,
        DocumentTitle documentTitle,
        DocumentContent documentContent,
        List<DocumentCategory> categoryList,
        DocumentInfo documentInfo,
        DocumentType type,
        EditedDateTime editedDateTime,
        DocumentEditor documentEditor
) {
}
