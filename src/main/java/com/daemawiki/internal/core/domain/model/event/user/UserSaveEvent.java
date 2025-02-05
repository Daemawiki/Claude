package com.daemawiki.internal.core.domain.model.event.user;

import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;

public record UserSaveEvent(
        UserInternalDTO userInternalDTO
) {

    public static UserSaveEvent create(final UserInternalDTO userInternalDTO) {
        return new UserSaveEvent(userInternalDTO);
    }

}
