package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.AbstractQueryDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.system.config.Page;

import java.util.List;

public abstract class AbstractTdChainQueryDef extends AbstractQueryDef implements QueryDef, TdChainQuery {
    protected FlexMapper flexMapper;

    public AbstractTdChainQueryDef(QueryStatement statement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
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
