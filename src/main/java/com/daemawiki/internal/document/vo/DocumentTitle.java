package com.daemawiki.internal.document.vo;

import com.daemawiki.internal.document.primitive.title.MainTitle;
import com.daemawiki.internal.document.primitive.title.SubTitle;
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
