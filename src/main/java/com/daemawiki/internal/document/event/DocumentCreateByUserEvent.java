package com.daemawiki.internal.document.event;

import com.daemawiki.internal.user.dto.UserInternalDTO;

public record DocumentCreateByUserEvent(
        UserInternalDTO userInternalDTO
) {

    public static DocumentCreateByUserEvent create(final UserInternalDTO userInternalDTO) {
        return new DocumentCreateByUserEvent(userInternalDTO);
    }

}
