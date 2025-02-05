package com.daemawiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
//@EnableRSocketSecurity
@EnableWebFluxSecurity
@SpringBootApplication
@ConfigurationPropertiesScan
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

/*
 *
 *   {presentation}        {application}          {domain}       - Layer
 *       [form]    ->     [internal dto]    ->    [entity]       - Data flow
 *             (mapping)                (mapping)                - Mapping needed
 *
 */
