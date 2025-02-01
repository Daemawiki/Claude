package com.daemawiki.external.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.multipart.DefaultPartHttpMessageReader;
import org.springframework.http.codec.multipart.MultipartHttpMessageReader;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
class WebConfiguration implements WebFluxConfigurer {

    @Value("${app.cors.allow-hosts}")
    private String allowHosts;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        final var hosts = allowHosts.split(",");

        registry.addMapping("/api/**")
                .allowedOrigins(hosts)
                .allowedMethods("POST", "GET", "PATCH", "DELETE", "PUT", "OPTIONS")
                .allowedHeaders("*");
    }

    @Override
    public void configureHttpMessageCodecs(final ServerCodecConfigurer configurer) {
        final var partReader = new DefaultPartHttpMessageReader();
        partReader.setMaxParts(1);
        partReader.setMaxDiskUsagePerPart(5L * 1024L * 1024L);
        partReader.setEnableLoggingRequestDetails(true);

        final var multipartReader = new MultipartHttpMessageReader(partReader);
        multipartReader.setEnableLoggingRequestDetails(true);
        configurer.defaultCodecs().multipartReader(multipartReader);
    }

}
