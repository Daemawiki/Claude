package com.daemawiki.internal.core.domain.model.dto.auth;

import com.daemawiki.internal.core.domain.model.primitive.auth.Password;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Generation;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Major;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Name;
import com.daemawiki.internal.core.domain.model.value.user.StudentInfo;

import java.util.List;

public record RegisterDTO(
        Name name,
        Email email,
        Password password,
        Generation generation,
        Major major,
        List<StudentInfo> studentInfoList
) {
}
