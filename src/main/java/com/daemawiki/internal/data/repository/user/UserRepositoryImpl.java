package com.daemawiki.internal.data.repository.user;

import com.daemawiki.internal.common.paging.PagingInfo;
import com.daemawiki.external.database.mongo.MongoDSL;
import com.daemawiki.external.database.mongo.MongoQueryUtils;
import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Generation;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Major;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
class UserRepositoryImpl extends UserAbstractRepository {

    private final MongoQueryUtils mongoQueryUtils;

    private UserRepositoryImpl(
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
            final PagingInfo pagingInfo
    ) {
        return mongoQueryUtils.find(
                buildQuery(generation.generation(), major.major(), pagingInfo),
                UserEntity.class
        ).map(userEntityMapper::toDTO);
    }

    private Query buildQuery(
            final Integer generation,
            final String major,
            final PagingInfo pagingInfo
    ) {
        return MongoDSL.createQuery()
                .where("generation", generation)
                .where("major", major)
                .paging(pagingInfo)
                .build();
    }

}
