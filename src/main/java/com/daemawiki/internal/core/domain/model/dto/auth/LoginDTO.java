package com.daemawiki.internal.core.domain.model.dto.auth;

import com.daemawiki.internal.core.domain.model.primitive.auth.Password;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;

public record LoginDTO(
        Email email,
        Password password
) {
}
