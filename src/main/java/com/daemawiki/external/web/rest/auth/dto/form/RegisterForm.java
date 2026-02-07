package com.daemawiki.external.web.rest.auth.dto.form;

import com.daemawiki.internal.user.primitive.Password;
import com.daemawiki.internal.user.primitive.personal.Email;
import com.daemawiki.internal.user.primitive.personal.Generation;
import com.daemawiki.internal.user.primitive.personal.Major;
import com.daemawiki.internal.user.primitive.personal.Name;
import com.daemawiki.internal.user.vo.StudentInfo;

import java.util.List;

public record RegisterForm(
        Name name,
        Email email,
        Password password,
        Generation generation,
        Major major,
        List<StudentInfo> studentInfoList
) {
}
