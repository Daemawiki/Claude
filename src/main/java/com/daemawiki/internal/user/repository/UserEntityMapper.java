package com.daemawiki.internal.user.repository;

import com.daemawiki.internal.user.dto.UserInternalDTO;
import com.daemawiki.internal.user.vo.StudentInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;
import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(
        componentModel = SPRING,
        unmappedTargetPolicy = ERROR,
        unmappedSourcePolicy = IGNORE
)
interface UserEntityMapper {

    @Mapping(
            target = "id",
            expression = "java(userInternalDTO.userId().value())",
            ignore = true
    )
    @Mapping(
            target = "role",
            expression = "java(userInternalDTO.userRole().name())"
    )
    @Mapping(
            target = "name",
            expression = "java(userInternalDTO.personalData().name().value())"
    )
    @Mapping(
            target = "email",
            expression = "java(userInternalDTO.personalData().email().value())"
    )
    @Mapping(
            target = "password",
            expression = "java(userInternalDTO.personalData().securedPassword().value())"
    )
    @Mapping(
            target = "documentId",
            expression = "java(userInternalDTO.documentId().value())",
            ignore = true
    )
    @Mapping(
            target = "generation",
            expression = "java(userInternalDTO.personalData().generation().value())"
    )
    @Mapping(
            target = "major",
            expression = "java(userInternalDTO.personalData().major().value())"
    )
    @Mapping(
            target = "studentInfoList",
            expression = "java(this.toStudentInfoEntityList(userInternalDTO.studentInfoList()))"
    )
    @Mapping(
            target = "registrationDate",
            expression = "java(userInternalDTO.registrationDate().value())",
            ignore = true
    )
    UserEntity toEntity(UserInternalDTO userInternalDTO);

    @Mapping(target = "updateDocumentId", ignore = true)

    @Mapping(
            target = "userId",
            expression = "java(UserId.create(userEntity.getId()))"
    )
    @Mapping(
            target = "userRole",
            expression = "java(UserRole.valueOf(userEntity.getRole()))"
    )
    @Mapping(
            target = "personalData.name",
            expression = "java(Name.create(userEntity.getName()))"
    )
    @Mapping(
            target = "personalData.email",
            expression = "java(Email.create(userEntity.getEmail()))"
    )
    @Mapping(
            target = "personalData.securedPassword",
            expression = "java(SecuredPassword.create(userEntity.getPassword()))"
    )
    @Mapping(
            target = "personalData.generation",
            expression = "java(Generation.create(userEntity.getGeneration()))"
    )
    @Mapping(
            target = "personalData.major",
            expression = "java(Major.create(userEntity.getMajor()))"
    )
    @Mapping(
            target = "documentId",
            expression = "java(DocumentId.create(userEntity.getDocumentId()))"
    )
    @Mapping(
            target = "studentInfoList",
            expression = "java(this.toStudentInfoDTOList(userEntity.getStudentInfoList()))"
    )
    @Mapping(
            target = "registrationDate",
            expression = "java(RegistrationDate.create(userEntity.getRegistrationDate().toString()))"
    )
    UserInternalDTO toDTO(UserEntity userEntity);

    @Mapping(
            target = "academicYear",
            expression = "java(studentInfo.academicYear().value())"
    )
    @Mapping(
            target = "studentGrade",
            expression = "java(studentInfo.studentGrade().value())"
    )
    @Mapping(
            target = "classNumber",
            expression = "java(studentInfo.classNumber().value())"
    )
    @Mapping(
            target = "studentNumber",
            expression = "java(studentInfo.studentNumber().value())"
    )
    UserEntity.StudentInfo toStudentInfoEntity(StudentInfo studentInfo);

    @Mapping(
            target = "academicYear",
            expression = "java(AcademicYear.create(userEntityStudentInfo.academicYear()))"
    )
    @Mapping(
            target = "studentGrade",
            expression = "java(StudentGrade.create(userEntityStudentInfo.studentGrade()))"
    )
    @Mapping(
            target = "classNumber",
            expression = "java(ClassNumber.create(userEntityStudentInfo.classNumber()))"
    )
    @Mapping(
            target = "studentNumber",
            expression = "java(StudentNumber.create(userEntityStudentInfo.studentNumber()))"
    )
    StudentInfo toStudentInfoDTO(UserEntity.StudentInfo userEntityStudentInfo);

    default List<UserEntity.StudentInfo> toStudentInfoEntityList(List<StudentInfo> studentInfoList) {
        return studentInfoList.stream()
                .map(this::toStudentInfoEntity)
                .toList();
    }

    default List<StudentInfo> toStudentInfoDTOList(List<UserEntity.StudentInfo> userEntityStudentInfoList) {
        return userEntityStudentInfoList.stream()
                .map(this::toStudentInfoDTO)
                .toList();
    }

}
