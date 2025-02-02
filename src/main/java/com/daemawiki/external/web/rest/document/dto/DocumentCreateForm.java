package com.daemawiki.external.web.rest.document.dto;

import com.daemawiki.internal.core.domain.model.primitive.document.DocumentCategory;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentType;
import com.daemawiki.internal.core.domain.model.value.document.DocumentTitle;

import java.util.List;

/**
 * <p>
 * example :
 * <pre>
 * in Application
 * {@code
 * final var request = DocumentCreateForm.create(
 *     DocumentTitle.create(
 *          MainTitle.create("김승원 문서 메인 타이틀"),
 *          SubTitle.create("김승원 문서 서브 타이틀")
 *     ),
 *     DocumentType.INCIDENT,
 *     List.of(
 *          DocumentCategory.create("학생"),
 *          DocumentCategory.create("9기"),
 *          DocumentCategory.create("백엔드"),
 *          DocumentCategory.create("최강")
 *     )
 * );
 * }
 *
 * in HTTP
 * {@code
 * POST: /api/document
 * Content-Type: application/json
 * Body:
 * {
 *     "title": {
 *         "main" : "김승원 문서 메인 타이틀",
 *         "sub" : "김승원 문서 서브 타이틀"
 *     },
 *     "document_type": "INCIDENT",
 *     "category_list": [
 *          {
 *              "category": "학생"
 *          },
 *          {
 *              "category": "9기"
 *          },
 *          {
 *              "category": "백엔드"
 *          },
 *          {
 *              "category": "최강"
 *          }
 *     ]
 * }
 * }
 * </pre>
 * </p>
 */
public record DocumentCreateForm(
        DocumentTitle documentTitle,
        DocumentType type,
        List<DocumentCategory> categoryList
) {

    public static DocumentCreateForm create(
            final DocumentTitle documentTitle,
            final DocumentType type,
            final List<DocumentCategory> categoryList
    ) {
        return new DocumentCreateForm(documentTitle, type, categoryList);
    }

}
