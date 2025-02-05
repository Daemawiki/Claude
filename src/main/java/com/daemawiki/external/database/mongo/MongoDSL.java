package com.daemawiki.external.database.mongo;

import com.daemawiki.internal.core.domain.model.value.shard.paging.PagingRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class MongoDSL {

    private final Query query;

    private MongoDSL() {
        this.query = new Query();
    }

    public static MongoDSL createQuery() {
        return new MongoDSL();
    }

    public MongoDSL where(
            final String field,
            final Object value
    ) {
        if (value != null) {
            query.addCriteria(Criteria.where(field).is(value));
        }
        return this;
    }

    public MongoDSL paging(final PagingRequest pagingRequest) {
        if (pagingRequest.sortProperty() != null) {
            final var direction = (pagingRequest.sortDirection().value() == 1)
                    ? Sort.Direction.ASC
                    : Sort.Direction.DESC;

            query.with(Sort.by(direction, pagingRequest.sortProperty().value()));
        }
        return this;
    }

    public Query build() {
        return query;
    }

}
