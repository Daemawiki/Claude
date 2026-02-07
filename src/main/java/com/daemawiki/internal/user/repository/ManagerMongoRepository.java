package com.daemawiki.internal.user.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

interface ManagerMongoRepository extends ReactiveMongoRepository<ManagerEntity, String> {

    Mono<ManagerEntity> findByEmail(String email);

    Mono<Void> deleteByEmail(String email);

}
