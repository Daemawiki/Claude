package com.daemawiki.external.web.rest.auth.dto;

import com.daemawiki.internal.core.domain.model.primitive.user.UserRole;

public record LoginResponse(
        String token,
        UserSimpleModel user
) {

    public static LoginResponse create(
            final String token,
            final String name,
            final UserRole role
    ) {
        return new LoginResponse(
                token,
                UserSimpleModel.create(name, role)
        );
    }

    private static record UserSimpleModel(
            String name,
            UserRole role
    ) {

        private static UserSimpleModel create(
                final String name,
                final UserRole role
        ) {
            return new UserSimpleModel(name, role);
        }

    }

}
