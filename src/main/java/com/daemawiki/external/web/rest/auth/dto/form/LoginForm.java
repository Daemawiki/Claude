package com.daemawiki.external.web.rest.auth.dto.form;

import com.daemawiki.internal.user.primitive.Password;
import com.daemawiki.internal.user.primitive.personal.Email;

public record LoginForm(
        Email email,
        Password password
) {
}
