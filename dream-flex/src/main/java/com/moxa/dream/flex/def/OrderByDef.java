package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.LimitStatement;
import com.moxa.dream.flex.invoker.FlexMarkInvokerStatement;

public interface OrderByDef<T extends LimitDef> extends LimitDef {

    default T limit(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(false);
        limitStatement.setFirst(new FlexMarkInvokerStatement(offset));
        limitStatement.setSecond(new FlexMarkInvokerStatement(rows));
        statement().setLimitStatement(limitStatement);
        return (T) creatorFactory().newLimitDef(statement());
    }

    default T offset(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(true);
        limitStatement.setFirst(new FlexMarkInvokerStatement(rows));
        limitStatement.setSecond(new FlexMarkInvokerStatement(offset));
        statement().setLimitStatement(limitStatement);
        return (T) creatorFactory().newLimitDef(statement());
    }
}
