package com.dream.wrap.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.WrapQueryFactory;
import com.dream.wrap.wrapper.AbstractQueryWrapper;
import com.dream.wrap.wrapper.OrderByWrapper;

public class DefaultOrderByWrapper extends AbstractQueryWrapper implements OrderByWrapper<DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultOrderByWrapper(QueryStatement statement, WrapQueryFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
