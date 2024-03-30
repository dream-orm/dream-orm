package com.dream.wrap.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.QueryCreatorFactory;
import com.dream.wrap.wrapper.AbstractQueryWrapper;
import com.dream.wrap.wrapper.QueryWrapper;

public class DefaultQueryWrapper extends AbstractQueryWrapper implements QueryWrapper {

    public DefaultQueryWrapper(QueryStatement statement, QueryCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
