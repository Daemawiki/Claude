package com.daemawiki.external.web.rest.document.dto.form;

import com.daemawiki.internal.core.domain.model.value.document.DocumentTitle;

public record DocumentEditTitleForm(
        DocumentTitle documentTitle
) {
}
