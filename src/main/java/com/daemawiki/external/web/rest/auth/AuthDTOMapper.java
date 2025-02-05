package com.daemawiki.external.web.rest.auth;

import com.daemawiki.external.web.rest.auth.dto.form.LoginForm;
import com.daemawiki.external.web.rest.auth.dto.form.RegisterForm;
import com.daemawiki.internal.core.domain.model.dto.auth.LoginDTO;
import com.daemawiki.internal.core.domain.model.dto.auth.RegisterDTO;
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
