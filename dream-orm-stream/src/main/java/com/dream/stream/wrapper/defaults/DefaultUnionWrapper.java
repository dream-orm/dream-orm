package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractQueryWrapper;
import com.dream.stream.wrapper.UnionWrapper;

public class DefaultUnionWrapper extends AbstractQueryWrapper implements UnionWrapper<DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultUnionWrapper(QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
