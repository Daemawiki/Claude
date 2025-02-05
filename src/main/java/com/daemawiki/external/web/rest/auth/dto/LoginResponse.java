package com.daemawiki.external.web.rest.auth.dto;

import com.daemawiki.internal.core.domain.model.primitive.auth.Token;
import com.daemawiki.internal.core.domain.model.primitive.user.UserRole;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Name;

public record LoginResponse(
        Token token,
        UserSimpleModel user
) {

    public static LoginResponse create(
            final Token token,
            final Name name,
            final UserRole role
    ) {
        return new LoginResponse(
                token,
                UserSimpleModel.create(name, role)
        );
    }

    private record UserSimpleModel(
            Name name,
            UserRole role
    ) {

        private static UserSimpleModel create(
                final Name name,
                final UserRole role
        ) {
            return new UserSimpleModel(name, role);
        }

    }

}
