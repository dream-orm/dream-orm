package com.dream.stream.wrapper;

import com.dream.stream.factory.StreamQueryFactory;
import com.dream.struct.command.Query;

public interface QueryWrapper<T> extends Query {
    Class<T> entityType();

    StreamQueryFactory creatorFactory();
}
