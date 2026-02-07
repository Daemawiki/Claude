package com.daemawiki.external.web.rest.document.dto.form;

import com.daemawiki.internal.user.vo.Editor;

import java.util.Set;

public record DocumentEditEditorSetForm(
        Set<Editor> editorList
) {
}
