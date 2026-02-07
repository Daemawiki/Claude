package com.daemawiki.external.web.rest.document.dto;

import com.daemawiki.internal.document.primitive.DocumentCategory;
import com.daemawiki.internal.document.primitive.DocumentId;
import com.daemawiki.internal.document.primitive.DocumentType;
import com.daemawiki.internal.document.primitive.content.TextBody;
import com.daemawiki.internal.document.primitive.title.MainTitle;
import com.daemawiki.internal.document.vo.DocumentInfo;
import com.daemawiki.internal.core.domain.model.value.shard.date.EditedDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DocumentHalfResponse(
        DocumentId documentId,
        @JsonProperty("mainTitle")
        MainTitle mainTitle,
        @JsonProperty("content")
        TextBody content,
        DocumentType type,
        List<DocumentCategory> categoryList,
        DocumentInfo documentInfo,
        EditedDateTime editedDateTime
) {
}
