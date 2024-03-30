package com.dream.chain.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.AbstractQueryDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.system.config.Page;
import com.dream.util.tree.Tree;

import java.util.List;

public abstract class AbstractChainQueryDef extends AbstractQueryDef implements QueryDef, ChainQuery {
    protected FlexMapper flexMapper;

    public AbstractChainQueryDef(QueryStatement statement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(statement, flexQueryFactory);
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
    public <T extends Tree> List<T> tree(Class<T> type) {
        return flexMapper.selectTree(this, type);
    }

    @Override
    public <T> Page<T> page(Class<T> type, Page page) {
        return flexMapper.selectPage(this, type, page);
    }

    @Override
    public boolean exists() {
        return flexMapper.exists(this);
    }
}
