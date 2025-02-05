package com.daemawiki.internal.data.repository.user;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

interface UserMongoRepository extends ReactiveMongoRepository<UserEntity, String> {

    Mono<Boolean> existsByEmail(String email);

    Mono<UserEntity> findByEmail(String email);

}
