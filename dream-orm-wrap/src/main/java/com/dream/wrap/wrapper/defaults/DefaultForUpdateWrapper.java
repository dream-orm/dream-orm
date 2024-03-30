package com.dream.wrap.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.QueryCreatorFactory;
import com.dream.wrap.wrapper.AbstractQueryWrapper;
import com.dream.wrap.wrapper.ForUpdateWrapper;

public class DefaultForUpdateWrapper extends AbstractQueryWrapper implements ForUpdateWrapper<DefaultQueryWrapper> {
    public DefaultForUpdateWrapper(QueryStatement statement, QueryCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
