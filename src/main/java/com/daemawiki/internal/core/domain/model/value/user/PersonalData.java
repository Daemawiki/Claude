package com.daemawiki.internal.core.domain.model.value.user;

import com.daemawiki.internal.core.domain.model.primitive.auth.SecuredPassword;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Generation;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Major;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Name;
import com.daemawiki.internal.core.domain.model.value.ValueObject;

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
