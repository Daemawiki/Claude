package com.daemawiki.internal.user.event;

import com.daemawiki.internal.user.dto.UserInternalDTO;

public record UserSaveEvent(
        UserInternalDTO userInternalDTO
) {

    public static UserSaveEvent create(final UserInternalDTO userInternalDTO) {
        return new UserSaveEvent(userInternalDTO);
    }

}
