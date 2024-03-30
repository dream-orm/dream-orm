package com.dream.wrap.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.QueryCreatorFactory;
import com.dream.wrap.wrapper.AbstractQueryWrapper;
import com.dream.wrap.wrapper.LimitWrapper;

public class DefaultLimitWrapper extends AbstractQueryWrapper implements LimitWrapper<DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultLimitWrapper(QueryStatement statement, QueryCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
