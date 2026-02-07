package com.daemawiki.internal.mail.event;

import com.daemawiki.internal.user.primitive.personal.Email;

public record MailSendEvent(
        Email to
) {

    public static MailSendEvent create(final Email to) {
        return new MailSendEvent(to);
    }

}
