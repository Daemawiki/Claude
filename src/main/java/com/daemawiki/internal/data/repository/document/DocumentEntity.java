package com.daemawiki.internal.data.repository.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Setter
@Getter
@NoArgsConstructor(access = PROTECTED)
@Document(collection = "daemawiki-document")
class DocumentEntity {

    @Id
    private String id;

    private Title title;

    private String content;

//    private List<Detail> detailList;
    private Map<String, String> detailMap;

    private List<String> categoryList;

    private Long viewCount;

    private Long version;

    private String type;

    private EditedDateTime editedDateTime;

    private Editor owner;

    private Set<Editor> editorSet;

    private DocumentEntity(
            final Title title,
            final Map<String, String> detailMap,
            final List<String> categoryList,
            final String type,
            final Editor owner
    ) {
        this.title = title;
        this.content = "";
        this.detailMap = detailMap;
        this.categoryList = categoryList;
        this.viewCount = 0L;
        this.version = 0L;
        this.type = type;
        this.editedDateTime = EditedDateTime.create();
        this.owner = owner;
        this.editorSet = Collections.emptySet();
    }

    @AllArgsConstructor
    static class EditedDateTime {

        private final LocalDateTime createdDateTime;

        @LastModifiedDate
        private LocalDateTime lastModifiedDateTime;

        public LocalDateTime createdDateTime() {
            return createdDateTime;
        }

        public LocalDateTime lastModifiedDateTime() {
            return lastModifiedDateTime;
        }

        public static EditedDateTime create() {
            final var now = LocalDateTime.now();

            return new EditedDateTime(now, now);
        }

        public static EditedDateTime create(
                final LocalDateTime createdDateTime,
                final LocalDateTime lastModifiedDateTime
        ) {
            return new EditedDateTime(createdDateTime, lastModifiedDateTime);
        }

    }

    record Title(
            String mainTitle,
            String subTitle
    ) {

        public static Title create(
                final String mainTitle,
                final String subTitle
        ) {
            return new Title(mainTitle, subTitle);
        }

    }

    record Detail(
            String key,
            String description
    ) {

        private static Detail create(
                final String name,
                final String description
        ) {
            return new Detail(name, description);
        }

    }

    record Editor(
            String name,
            String userId
    ) {

        public static Editor create(
                final String name,
                final String userId
        ) {
            return new Editor(name, userId);
        }

    }

}
