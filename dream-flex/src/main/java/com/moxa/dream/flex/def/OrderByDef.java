package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.LimitStatement;
import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.invoker.FlexMarkInvokerStatement;

public class OrderByDef extends LimitDef {

    protected OrderByDef(QueryStatement statement) {
        super(statement);
    }

    public LimitDef limit(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(false);
        limitStatement.setFirst(new FlexMarkInvokerStatement(offset));
        limitStatement.setSecond(new FlexMarkInvokerStatement(rows));
        statement.setLimitStatement(limitStatement);
        return new LimitDef(statement);
    }

    public LimitDef offset(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(true);
        limitStatement.setFirst(new FlexMarkInvokerStatement(rows));
        limitStatement.setSecond(new FlexMarkInvokerStatement(offset));
        statement.setLimitStatement(limitStatement);
        return new LimitDef(statement);
    }
}
