package com.daemawiki.external.exchange.response;

import org.springframework.web.server.ServerWebExchange;

public interface ResponseWriterFactory {

    ResponseWriter getWriter(ServerWebExchange exchange);
}

