package com.dream.chain.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.AbstractQueryDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.system.config.Page;

import java.util.List;

public class ChainQueryDef extends AbstractQueryDef implements QueryDef {
    protected FlexMapper flexMapper;

    public ChainQueryDef(QueryStatement statement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(statement, flexQueryFactory);
        this.flexMapper = flexMapper;
    }

    public <T> T one(Class<T> type) {
        return flexMapper.selectOne(this, type);
    }

    public <T> List<T> list(Class<T> type) {
        return flexMapper.selectList(this, type);
    }

    public <T> Page<T> page(Class<T> type, Page page) {
        return flexMapper.selectPage(this, type, page);
    }

    public boolean exists() {
        return flexMapper.exists(this);
    }
}
