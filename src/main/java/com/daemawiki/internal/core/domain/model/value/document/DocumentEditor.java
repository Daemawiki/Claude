package com.daemawiki.internal.core.domain.model.value.document;

import com.daemawiki.internal.core.domain.model.value.user.Editor;

import java.util.Set;

public record DocumentEditor(
        Editor owner,
        Set<Editor> editorSet
) {

    public DocumentEditor updateEditorSet(final Set<Editor> editorSet) {
        return new DocumentEditor(owner, editorSet);
    }

    public static DocumentEditor create(
            final Editor owner,
            final Set<Editor> editorSet
    ) {
        return new DocumentEditor(owner, editorSet);
    }

}
