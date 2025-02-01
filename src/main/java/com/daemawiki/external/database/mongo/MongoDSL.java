package com.daemawiki.external.database.mongo;

import com.daemawiki.internal.common.paging.PagingInfo;
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

    public MongoDSL paging(final PagingInfo pagingInfo) {
        if (pagingInfo.sortBy() != null) {
            final var direction = (pagingInfo.sortDirection() == 1)
                    ? Sort.Direction.ASC
                    : Sort.Direction.DESC;

            query.with(Sort.by(direction, pagingInfo.sortBy().getPath()));
        }
        return this;
    }

    public Query build() {
        return query;
    }

}
