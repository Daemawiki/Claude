package com.daemawiki.external.api.rest.auth;

import com.daemawiki.external.api.rest.auth.dto.LoginForm;
import com.daemawiki.external.api.rest.auth.dto.RegisterForm;
import com.daemawiki.internal.user.dto.LoginDTO;
import com.daemawiki.internal.user.dto.RegisterDTO;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;
import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(
        componentModel = SPRING,
        unmappedTargetPolicy = ERROR,
        unmappedSourcePolicy = IGNORE
)
interface AuthDTOMapper {

    RegisterDTO toRegisterDTO(RegisterForm source);

    LoginDTO toLoginDTO(LoginForm source);

}
