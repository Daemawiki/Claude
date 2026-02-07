package com.daemawiki.internal.user.repository;

import com.daemawiki.external.database.mongo.MongoDSL;
import com.daemawiki.external.database.mongo.MongoQueryUtils;
import com.daemawiki.internal.user.dto.UserInternalDTO;
import com.daemawiki.internal.user.primitive.personal.Generation;
import com.daemawiki.internal.user.primitive.personal.Major;
import com.daemawiki.internal.common.value.shard.PagingRequest;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
class UserRepositoryImpl extends UserAbstractRepository {

    private final MongoQueryUtils mongoQueryUtils;

    UserRepositoryImpl(
            final UserMongoRepository userMongoRepository,
            final UserEntityMapper userEntityMapper,
            final MongoQueryUtils mongoQueryUtils
    ) {
        super(userMongoRepository, userEntityMapper);
        this.mongoQueryUtils = mongoQueryUtils;
    }

    @Override
    public Flux<UserInternalDTO> findByGenerationAndMajor(
            final Generation generation,
            final Major major,
            final PagingRequest pagingRequest
    ) {
        return mongoQueryUtils.find(
                buildQuery(generation.generation(), major.major(), pagingRequest),
                UserEntity.class
        ).map(userEntityMapper::toDTO);
    }

    private Query buildQuery(
            final Integer generation,
            final String major,
            final PagingRequest pagingRequest
    ) {
        return MongoDSL.createQuery()
                .where("generation", generation)
                .where("major", major)
                .paging(pagingRequest)
                .build();
    }

}
