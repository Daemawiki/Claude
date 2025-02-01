package com.daemawiki.archive.daemawiki.domain.manager.repository;

import com.daemawiki.archive.daemawiki.domain.manager.model.ManagerEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

interface ManagerMongoRepository extends ReactiveMongoRepository<ManagerEntity, String> {
    Mono<ManagerEntity> findByEmail(String email);
}
