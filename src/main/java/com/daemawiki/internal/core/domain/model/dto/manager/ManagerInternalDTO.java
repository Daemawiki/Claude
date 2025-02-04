package com.daemawiki.internal.core.domain.model.dto.manager;

import com.daemawiki.internal.core.domain.model.primitive.manager.ManagerId;
import com.daemawiki.internal.core.domain.model.primitive.user.UserId;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;

public record ManagerInternalDTO(
        ManagerId managerId,
        Email email,
        UserId userId
) {
}
