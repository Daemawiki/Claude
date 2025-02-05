package com.daemawiki.internal.core.domain.model.dto.mail;

import com.daemawiki.internal.core.domain.model.primitive.mail.AuthCode;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;

public record AuthCodeDTO(
        Email email,
        AuthCode authCode
) {

    public static AuthCodeDTO create(
            final Email email,
            final AuthCode authCode
    ) {
        return new AuthCodeDTO(email, authCode);
    }

}
