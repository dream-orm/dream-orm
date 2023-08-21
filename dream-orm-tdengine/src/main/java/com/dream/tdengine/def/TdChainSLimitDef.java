package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.invoker.FlexMarkInvokerStatement;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdQueryStatement;
import com.dream.tdengine.statement.TdSLimitStatement;

public class TdChainSLimitDef extends TdChainOrderByDef {
    public TdChainSLimitDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }

    public TdChainOrderByDef sLimit(Integer offset, Integer rows) {
        TdSLimitStatement tdSLimitStatement = new TdSLimitStatement();
        tdSLimitStatement.setOffset(false);
        tdSLimitStatement.setFirst(new FlexMarkInvokerStatement(offset));
        tdSLimitStatement.setSecond(new FlexMarkInvokerStatement(rows));
        ((TdQueryStatement) statement()).setSlimit(tdSLimitStatement);
        return (TdChainOrderByDef) creatorFactory().newOrderByDef(statement());
    }

    public TdChainOrderByDef sOffset(Integer offset, Integer rows) {
        TdSLimitStatement tdSLimitStatement = new TdSLimitStatement();
        tdSLimitStatement.setOffset(true);
        tdSLimitStatement.setFirst(new FlexMarkInvokerStatement(rows));
        tdSLimitStatement.setSecond(new FlexMarkInvokerStatement(offset));
        ((TdQueryStatement) statement()).setSlimit(tdSLimitStatement);
        return (TdChainOrderByDef) creatorFactory().newOrderByDef(statement());
    }
}
