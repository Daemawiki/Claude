package com.daemawiki.external.api.rest.document.dto;

import com.daemawiki.internal.user.vo.Editor;

import java.util.Set;

public record DocumentEditEditorSetForm(
        Set<Editor> editorList
) {
}
