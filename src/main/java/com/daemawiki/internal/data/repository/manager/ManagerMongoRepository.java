package com.daemawiki.internal.data.repository.manager;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

interface ManagerMongoRepository extends ReactiveMongoRepository<ManagerEntity, String> {

    Mono<ManagerEntity> findByEmail(String email);

    Mono<Void> deleteByEmail(String email);

}
