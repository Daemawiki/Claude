package com.daemawiki.internal.core.domain.model.event.document;

import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;

public record DocumentCreateByUserEvent(
        UserInternalDTO userInternalDTO
) {

    public static DocumentCreateByUserEvent create(final UserInternalDTO userInternalDTO) {
        return new DocumentCreateByUserEvent(userInternalDTO);
    }

}
