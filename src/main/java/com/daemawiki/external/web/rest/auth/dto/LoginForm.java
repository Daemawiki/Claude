package com.daemawiki.external.web.rest.auth.dto;

import com.daemawiki.internal.core.domain.model.primitive.auth.Password;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;

public record LoginForm(
        Email email,
        Password password
) {
}
