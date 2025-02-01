package com.daemawiki.internal.core.domain.model.dto.user;

import com.daemawiki.internal.core.domain.model.dto.auth.RegisterDTO;
import com.daemawiki.internal.core.domain.model.primitive.auth.SecuredPassword;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.domain.model.primitive.user.UserRole;
import com.daemawiki.internal.core.domain.model.primitive.user.UserId;
import com.daemawiki.internal.core.domain.model.primitive.user.RegistrationDate;
import com.daemawiki.internal.core.domain.model.value.user.PersonalData;
import com.daemawiki.internal.core.domain.model.value.user.StudentInfo;

import java.util.List;

public record UserInternalDTO(
        UserId userId,
        UserRole userRole,
        PersonalData personalData,
        DocumentId documentId,
        List<StudentInfo> studentInfoList,
        RegistrationDate registrationDate
) {

    public UserInternalDTO setDocumentId(final DocumentId documentId) {
        return new UserInternalDTO(userId, userRole, personalData, documentId, studentInfoList, registrationDate);
    }

    public static UserInternalDTO create(
            final UserId userId,
            final UserRole userRole,
            final PersonalData personalData,
            final DocumentId documentId,
            final List<StudentInfo> studentInfoList,
            final RegistrationDate registrationDate
    ) {
        return new UserInternalDTO(userId, userRole, personalData, documentId, studentInfoList, registrationDate);
    }

    public static UserInternalDTO create(
            final RegisterDTO registerDTO,
            final String encodedPassword,
            final UserRole userRole
    ) {
        return create(
                null,
                userRole,
                PersonalData.create(
                        registerDTO.name(),
                        registerDTO.email(),
                        SecuredPassword.create(encodedPassword),
                        registerDTO.generation(),
                        registerDTO.major()
                ),
                null,
                registerDTO.studentInfoList(),
                null
        );
    }

}
