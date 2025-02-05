package com.daemawiki.internal.data.repository.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Setter
@Getter
@NoArgsConstructor(access = PROTECTED)
@Document(collection = "daemawiki-user")
class UserEntity {
    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    @Indexed(unique = true)
    private String documentId;

    private Integer generation;

    private String major;

    private List<StudentInfo> studentInfoList;

    @CreatedDate
    private LocalDateTime registrationDate;

    private String role;

    public void updateDocumentId(final String documentId) { // TODO: 2/4/25
        this.documentId = documentId;
    }

    public void changePassword(final String password) { // TODO: 2/4/25
        this.password = password;
    }

    record StudentInfo(
            Integer academicYear,
            Integer studentGrade,
            Integer classNumber,
            Integer studentNumber
    ) {
        public static StudentInfo create(
                final Integer academicYear,
                final Integer studentGrade,
                final Integer classNumber,
                final Integer studentNumber
        ) {
            return new StudentInfo(academicYear, studentGrade, classNumber, studentNumber);
        }
    }

}
