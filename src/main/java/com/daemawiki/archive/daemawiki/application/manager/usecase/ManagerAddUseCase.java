package com.daemawiki.archive.daemawiki.application.manager.usecase;

import reactor.core.publisher.Mono;

public interface ManagerAddUseCase {
    Mono<Void> add(String email);
}
