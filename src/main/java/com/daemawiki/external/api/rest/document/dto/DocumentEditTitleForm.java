package com.daemawiki.external.api.rest.document.dto;

import com.daemawiki.internal.document.vo.DocumentTitle;

public record DocumentEditTitleForm(
        DocumentTitle documentTitle
) {
}
