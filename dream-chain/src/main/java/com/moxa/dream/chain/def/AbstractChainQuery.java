package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.AbstractQuery;
import com.moxa.dream.flex.def.Query;
import com.moxa.dream.flex.factory.QueryCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;
import com.moxa.dream.system.config.Page;

import java.util.List;

public abstract class AbstractChainQuery extends AbstractQuery implements Query, ChainQuery {
    private FlexMapper flexMapper;

    public AbstractChainQuery(QueryStatement statement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(statement, queryCreatorFactory);
        this.flexMapper = flexMapper;
    }

    @Override
    public <T> T one(Class<T> type) {
        return flexMapper.selectOne(this, type);
    }

    @Override
    public <T> List<T> list(Class<T> type) {
        return flexMapper.selectList(this, type);
    }

    @Override
    public <T> Page<T> page(Class<T> type, Page page) {
        return flexMapper.selectPage(this, type, page);
    }
}
