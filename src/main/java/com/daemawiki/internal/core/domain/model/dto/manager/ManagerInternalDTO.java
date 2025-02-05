package com.daemawiki.internal.core.domain.model.dto.manager;

import com.daemawiki.internal.core.domain.model.primitive.manager.ManagerId;
import com.daemawiki.internal.core.domain.model.primitive.user.UserId;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;

public record ManagerInternalDTO(
        ManagerId managerId,
        Email email,
        UserId userId
) {

    public ManagerInternalDTO updateUserId(final UserId userId) {
        return create(managerId, email, userId);
    }

    public static ManagerInternalDTO create(
            final Email email
    ) {
        return create(null, email, null);
    }

    public static ManagerInternalDTO create(
            final Email email,
            final UserId userId
    ) {
        return create(null, email, userId);
    }

    public static ManagerInternalDTO create(
            final ManagerId managerId,
            final Email email,
            final UserId userId
    ) {
        return new ManagerInternalDTO(managerId, email, userId);
    }

}
