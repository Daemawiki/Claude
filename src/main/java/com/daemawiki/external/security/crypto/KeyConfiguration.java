package com.daemawiki.external.security.crypto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Configuration
class KeyConfiguration {

    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException {
        final var keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);

        return keyGen.generateKeyPair();
    }

    @Bean
    public SecretKey sharedSecret() throws NoSuchAlgorithmException {
        final var keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(256);

        return keyGenerator.generateKey();
    }

}
