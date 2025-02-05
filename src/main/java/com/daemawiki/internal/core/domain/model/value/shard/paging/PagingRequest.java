package com.daemawiki.internal.core.domain.model.value.shard.paging;

import com.daemawiki.internal.core.domain.model.primitive.shard.paging.PageNumber;
import com.daemawiki.internal.core.domain.model.primitive.shard.paging.SizeNumber;
import com.daemawiki.internal.core.domain.model.primitive.shard.paging.SortDirection;
import com.daemawiki.internal.core.domain.model.primitive.shard.paging.SortProperty;
import com.daemawiki.internal.core.domain.model.value.ValueObject;

public record PagingRequest(
        SortProperty sortProperty,
        SortDirection sortDirection,
        PageNumber pageNumber,
        SizeNumber sizeNumber
) implements ValueObject {
}
