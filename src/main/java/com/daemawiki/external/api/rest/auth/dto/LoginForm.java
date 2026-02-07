package com.daemawiki.external.api.rest.auth.dto;

import com.daemawiki.internal.user.primitive.Password;
import com.daemawiki.internal.user.primitive.personal.Email;

public record LoginForm(
        Email email,
        Password password
) {
}
