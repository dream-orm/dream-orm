package com.dream.flex.def;

import com.dream.antlr.smt.LimitStatement;
import com.dream.flex.invoker.FlexMarkInvokerStatement;

public interface OrderByDef<Limit extends LimitDef, Union extends UnionDef, ForUpdate extends ForUpdateDef> extends LimitDef<Union, ForUpdate> {

    default Limit limit(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(false);
        limitStatement.setFirst(new FlexMarkInvokerStatement(offset));
        limitStatement.setSecond(new FlexMarkInvokerStatement(rows));
        statement().setLimitStatement(limitStatement);
        return (Limit) creatorFactory().newLimitDef(statement());
    }

    default Limit offset(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(true);
        limitStatement.setFirst(new FlexMarkInvokerStatement(rows));
        limitStatement.setSecond(new FlexMarkInvokerStatement(offset));
        statement().setLimitStatement(limitStatement);
        return (Limit) creatorFactory().newLimitDef(statement());
    }
}
