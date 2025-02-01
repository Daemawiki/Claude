package com.daemawiki.external.mail;

import com.daemawiki.internal.data.property.MailSenderProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
class MailSenderConfig {

    private final MailSenderProperties mailProperties;

    private static final String MAIL_SMTP_PREFIX = "mail.smtp.";
    private static final String UTF_8 = "UTF-8";

    @Bean
    JavaMailSender javaMailService() {
        final var javaMailSender = new JavaMailSenderImpl();
        configureMailSender(javaMailSender);

        return javaMailSender;
    }

    private void configureMailSender(final JavaMailSenderImpl javaMailSender) {
        javaMailSender.setHost(mailProperties.host());
        javaMailSender.setUsername(mailProperties.email());
        javaMailSender.setPassword(mailProperties.password());
        javaMailSender.setPort(mailProperties.port());
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding(UTF_8);
    }

    private Properties getMailProperties() {
        final var properties = new Properties();

        properties.put(MAIL_SMTP_PREFIX + "socketFactory.port", mailProperties.port());
        properties.put(MAIL_SMTP_PREFIX + "auth", mailProperties.auth());
        properties.put(MAIL_SMTP_PREFIX + "starttls.enable", mailProperties.starttlsEnable());
        properties.put(MAIL_SMTP_PREFIX + "starttls.required", mailProperties.starttlsRequired());
        properties.put(MAIL_SMTP_PREFIX + "socketFactory.fallback", mailProperties.socketFactoryFallback());
        properties.put(MAIL_SMTP_PREFIX + "socketFactory.class", mailProperties.socketFactoryClass());
        properties.put(MAIL_SMTP_PREFIX + "ssl.checkserveridentity", "true");

        return properties;
    }

}