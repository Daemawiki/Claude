package com.daemawiki.internal.user.dto;

import com.daemawiki.internal.user.primitive.SecuredPassword;
import com.daemawiki.internal.document.primitive.DocumentId;
import com.daemawiki.internal.user.primitive.UserRole;
import com.daemawiki.internal.user.primitive.UserId;
import com.daemawiki.internal.user.primitive.RegistrationDate;
import com.daemawiki.internal.user.vo.PersonalData;
import com.daemawiki.internal.user.vo.StudentInfo;

import java.util.List;

public record UserInternalDTO(
        UserId userId,
        UserRole userRole,
        PersonalData personalData,
        DocumentId documentId,
        List<StudentInfo> studentInfoList,
        RegistrationDate registrationDate
) {

    public UserInternalDTO updateRoleToManager() {
        return updateUserRole(UserRole.MANAGER);
    }

    public UserInternalDTO updateRoleToMop() {
        return updateUserRole(UserRole.MOP);
    }

    private UserInternalDTO updateUserRole(final UserRole userRole) {
        return new UserInternalDTO(userId, userRole, personalData, documentId, studentInfoList, registrationDate);
    }

    public UserInternalDTO updateDocumentId(final DocumentId documentId) {
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
