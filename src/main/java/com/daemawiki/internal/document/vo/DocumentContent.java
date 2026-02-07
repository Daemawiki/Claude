package com.daemawiki.internal.document.vo;

import com.daemawiki.internal.document.primitive.content.TextBody;
import com.daemawiki.internal.document.primitive.detail.Description;
import com.daemawiki.internal.document.primitive.detail.DetailKey;
import com.daemawiki.internal.common.value.ValueObject;

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
