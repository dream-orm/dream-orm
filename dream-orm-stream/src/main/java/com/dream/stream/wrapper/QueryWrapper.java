package com.dream.stream.wrapper;

import com.dream.struct.command.Query;
import com.dream.stream.factory.StreamQueryFactory;

public interface QueryWrapper extends Query {
    StreamQueryFactory creatorFactory();
}
