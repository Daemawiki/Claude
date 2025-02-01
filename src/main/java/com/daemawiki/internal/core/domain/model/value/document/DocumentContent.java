package com.daemawiki.internal.core.domain.model.value.document;

import com.daemawiki.internal.core.domain.model.primitive.document.content.TextBody;
import com.daemawiki.internal.core.domain.model.primitive.document.detail.Description;
import com.daemawiki.internal.core.domain.model.primitive.document.detail.DetailKey;
import com.daemawiki.internal.core.domain.model.value.ValueObject;

import java.util.Map;

public record DocumentContent(
        TextBody textBody,
        Map<DetailKey, Description> detailMap
) implements ValueObject {

    public static DocumentContent create(
            final TextBody textBody,
            final Map<DetailKey, Description> detailMap
    ) {
        return new DocumentContent(textBody, detailMap);
    }

}
