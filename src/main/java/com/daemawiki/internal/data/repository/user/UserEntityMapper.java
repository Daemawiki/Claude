package com.daemawiki.internal.data.repository.user;

import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.value.user.StudentInfo;
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
            expression = "java(source.userId().value())",
            ignore = true
    )
    @Mapping(
            target = "role",
            expression = "java(source.role().name())"
    )
    @Mapping(
            target = "name",
            expression = "java(source.personalData().name().value())"
    )
    @Mapping(
            target = "email",
            expression = "java(source.personalData().email().value())"
    )
    @Mapping(
            target = "password",
            expression = "java(source.personalData().securedPassword().value())"
    )
    @Mapping(
            target = "documentId",
            expression = "java(source.documentId().value())",
            ignore = true
    )
    @Mapping(
            target = "generation",
            expression = "java(source.generation().value())"
    )
    @Mapping(
            target = "major",
            expression = "java(source.major().value())"
    )
    @Mapping(
            target = "studentInfoList",
            expression = "java(this.toStudentInfoEntityList(source.studentInfoList()))"
    )
    @Mapping(
            target = "registrationDate",
            expression = "java(source.registrationDate().value())",
            ignore = true
    )
    UserEntity toEntity(UserInternalDTO source);

    @Mapping(
            target = "userId",
            expression = "java(UserId.create(source.getId()))"
    )
    @Mapping(
            target = "userRole",
            expression = "java(UserRole.valueOf(source.getRole()))"
    )
    @Mapping(
            target = "personalData.name",
            expression = "java(Name.create(source.getName()))"
    )
    @Mapping(
            target = "personalData.email",
            expression = "java(Email.create(source.getEmail()))"
    )
    @Mapping(
            target = "personalData.securedPassword",
            expression = "java(SecuredPassword.create(source.getPassword()))"
    )
    @Mapping(
            target = "personalData.generation",
            expression = "java(Generation.create(source.getGeneration()))"
    )
    @Mapping(
            target = "personalData.major",
            expression = "java(Major.create(source.getMajor()))"
    )
    @Mapping(
            target = "documentId",
            expression = "java(DocumentId.create(source.documentId()))"
    )
    @Mapping(
            target = "studentInfoList",
            expression = "java(this.toStudentInfoDTOList(source.getStudentInfoList()))"
    )
    @Mapping(
            target = "registrationDate",
            expression = "java(RegistrationDate.create(source.getRegistrationDate()))"
    )
    UserInternalDTO toDTO(UserEntity source);

    @Mapping(
            target = "academicYear",
            expression = "java(source.academicYear().value())"
    )
    @Mapping(
            target = "studentGrade",
            expression = "java(source.studentGrade().value())"
    )
    @Mapping(
            target = "classNumber",
            expression = "java(source.classNumber().value())"
    )
    @Mapping(
            target = "studentNumber",
            expression = "java(source.studentNumber().value())"
    )
    UserEntity.StudentInfo toStudentInfoEntity(StudentInfo source);

    @Mapping(
            target = "academicYear",
            expression = "java(AcademicYear.create(source.academicYear()))"
    )
    @Mapping(
            target = "studentGrade",
            expression = "java(StudentGrade.create(source.studentGrade()))"
    )
    @Mapping(
            target = "classNumber",
            expression = "java(ClassNumber.create(source.classNumber()))"
    )
    @Mapping(
            target = "studentNumber",
            expression = "java(StudentNumber.create(source.studentNumber()))"
    )
    StudentInfo toStudentInfoDTO(UserEntity.StudentInfo source);

    default List<UserEntity.StudentInfo> toStudentInfoEntityList(List<StudentInfo> source) {
        return source.stream()
                .map(this::toStudentInfoEntity)
                .toList();
    }

    default List<StudentInfo> toStudentInfoDTOList(List<UserEntity.StudentInfo> source) {
        return source.stream()
                .map(this::toStudentInfoDTO)
                .toList();
    }

}
