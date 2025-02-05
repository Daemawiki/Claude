package com.daemawiki.internal.data.repository.manager;

import com.daemawiki.internal.core.domain.model.dto.manager.ManagerInternalDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.ERROR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(
        componentModel = SPRING,
        unmappedTargetPolicy = ERROR,
        unmappedSourcePolicy = IGNORE
)
interface ManagerEntityMapper {

    @Mapping(target = "updateUserId", ignore = true) // mapstruct method 오류 탐지 방지

    @Mapping(
            target = "managerId",
            expression = "java(ManagerId.create(source.getId()))"
    )
    @Mapping(
            target = "email",
            expression = "java(Email.create(source.getEmail()))"
    )
    @Mapping(
            target = "userId",
            expression = "java(UserId.create(source.getUserId()))"
    )
    ManagerInternalDTO toDTO(ManagerEntity source);

    @Mapping(
            target = "id",
            expression = "java(source.managerId().value())",
            ignore = true
    )
    @Mapping(
            target = "email",
            expression = "java(source.email().value())"
    )
    @Mapping(
            target = "userId",
            expression = "java(source.userId().value())",
            ignore = true
    )
    ManagerEntity toEntity(ManagerInternalDTO source);

}
