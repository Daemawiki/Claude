package com.daemawiki.internal.common.value.shard;

import com.daemawiki.internal.common.primitive.shard.PageNumber;
import com.daemawiki.internal.common.primitive.shard.SizeNumber;
import com.daemawiki.internal.common.primitive.shard.SortDirection;
import com.daemawiki.internal.common.primitive.shard.SortProperty;
import com.daemawiki.internal.common.value.ValueObject;

public record PagingRequest(
        SortProperty sortProperty,
        SortDirection sortDirection,
        PageNumber pageNumber,
        SizeNumber sizeNumber
) implements ValueObject {
}
