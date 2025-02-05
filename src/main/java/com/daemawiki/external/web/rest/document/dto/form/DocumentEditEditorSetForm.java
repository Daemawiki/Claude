package com.daemawiki.external.web.rest.document.dto.form;

import com.daemawiki.internal.core.domain.model.value.user.Editor;

import java.util.Set;

public record DocumentEditEditorSetForm(
        Set<Editor> editorList
) {
}
