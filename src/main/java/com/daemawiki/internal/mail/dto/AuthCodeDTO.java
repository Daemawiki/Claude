package com.daemawiki.internal.mail.dto;

import com.daemawiki.internal.mail.primitive.AuthCode;
import com.daemawiki.internal.user.primitive.personal.Email;

public record AuthCodeDTO(
        Email email,
        com.daemawiki.internal.mail.primitive.AuthCode authCode
) {

    public static AuthCodeDTO create(
            final Email email,
            final AuthCode authCode
    ) {
        return new AuthCodeDTO(email, authCode);
    }

}
