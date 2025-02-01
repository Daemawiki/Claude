package com.daemawiki.internal.core.domain.model.value.document;

import com.daemawiki.internal.core.domain.model.primitive.document.title.MainTitle;
import com.daemawiki.internal.core.domain.model.primitive.document.title.SubTitle;
import com.daemawiki.internal.core.domain.model.value.ValueObject;

public record DocumentTitle(
        MainTitle mainTitle,
        SubTitle subTitle
) implements ValueObject {

    public static DocumentTitle create(
            final MainTitle mainTitle,
            final SubTitle subTitle
    ) {
        return new DocumentTitle(mainTitle, subTitle);
    }

}
