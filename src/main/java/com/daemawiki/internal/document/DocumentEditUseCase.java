package com.daemawiki.internal.document;

import com.daemawiki.internal.document.primitive.DocumentId;
import com.daemawiki.internal.document.vo.DocumentContent;
import com.daemawiki.internal.document.vo.DocumentTitle;
import com.daemawiki.internal.user.vo.Editor;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface DocumentEditUseCase {

    Mono<Void> editContents(
            DocumentId documentId,
            DocumentContent content
    );

    Mono<Void> editTitle(
            DocumentId documentId,
            DocumentTitle title
    );

    Mono<Void> editEditorSet(
            DocumentId documentId,
            Set<Editor> editorSet
    );

}
