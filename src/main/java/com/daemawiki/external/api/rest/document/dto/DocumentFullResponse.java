package com.daemawiki.external.api.rest.document.dto;

import com.daemawiki.internal.document.primitive.DocumentCategory;
import com.daemawiki.internal.document.primitive.DocumentId;
import com.daemawiki.internal.document.primitive.DocumentType;
import com.daemawiki.internal.document.vo.DocumentContent;
import com.daemawiki.internal.document.vo.DocumentEditor;
import com.daemawiki.internal.document.vo.DocumentInfo;
import com.daemawiki.internal.document.vo.DocumentTitle;
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
