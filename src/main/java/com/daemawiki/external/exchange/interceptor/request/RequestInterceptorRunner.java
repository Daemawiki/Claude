package com.daemawiki.external.exchange.interceptor.request;

import com.daemawiki.external.exchange.interceptor.HandlerExchangeInterceptorRunner;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface RequestInterceptorRunner extends HandlerExchangeInterceptorRunner {

    @Override
    Mono<Void> run(ServerWebExchange exchange);
}
