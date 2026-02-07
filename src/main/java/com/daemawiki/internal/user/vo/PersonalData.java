package com.daemawiki.internal.user.vo;

import com.daemawiki.internal.user.primitive.SecuredPassword;
import com.daemawiki.internal.user.primitive.personal.Email;
import com.daemawiki.internal.user.primitive.personal.Generation;
import com.daemawiki.internal.user.primitive.personal.Major;
import com.daemawiki.internal.user.primitive.personal.Name;
import com.daemawiki.internal.common.value.ValueObject;

public record PersonalData(
        Name name,
        Email email,
        SecuredPassword securedPassword,
        Generation generation,
        Major major
) implements ValueObject {

    public static PersonalData create(
            final Name name,
            final Email email,
            final SecuredPassword securedPassword,
            final Generation generation,
            final Major major
    ) {
        return new PersonalData(name, email, securedPassword, generation, major);
    }

}
