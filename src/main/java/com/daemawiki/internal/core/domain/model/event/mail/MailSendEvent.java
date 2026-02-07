package com.daemawiki.internal.core.domain.model.event.mail;

import com.daemawiki.internal.user.primitive.personal.Email;

public record MailSendEvent(
        Email to
) {

    public static MailSendEvent create(final Email to) {
        return new MailSendEvent(to);
    }

}
