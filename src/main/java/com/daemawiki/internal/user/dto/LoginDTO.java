package com.daemawiki.internal.user.dto;

import com.daemawiki.internal.user.primitive.Password;
import com.daemawiki.internal.user.primitive.personal.Email;

public record LoginDTO(
        Email email,
        Password password
) {
}
