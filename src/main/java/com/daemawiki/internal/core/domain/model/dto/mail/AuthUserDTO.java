package com.daemawiki.internal.core.domain.model.dto.mail;

import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;

public record AuthUserDTO(
        Email email
) {

    public static AuthUserDTO of(final Email email) {
        return new AuthUserDTO(email);
    }

}
