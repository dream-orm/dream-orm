package com.dream.wrap.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.QueryCreatorFactory;
import com.dream.wrap.wrapper.AbstractQueryWrapper;
import com.dream.wrap.wrapper.UnionWrapper;

public class DefaultUnionWrapper extends AbstractQueryWrapper implements UnionWrapper<DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultUnionWrapper(QueryStatement statement, QueryCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
