package com.dream.flex.def;

import com.dream.antlr.smt.LimitStatement;
import com.dream.flex.invoker.FlexMarkInvokerStatement;

public interface LimitDef<
        Union extends UnionDef<ForUpdate, Query>,
        ForUpdate extends ForUpdateDef<Query>,
        Query extends QueryDef>
        extends UnionDef<ForUpdate, Query> {

    default Union limit(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(false);
        limitStatement.setFirst(new FlexMarkInvokerStatement(offset));
        limitStatement.setSecond(new FlexMarkInvokerStatement(rows));
        statement().setLimitStatement(limitStatement);
        return (Union) creatorFactory().newUnionDef(statement());
    }

    default Union offset(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(true);
        limitStatement.setFirst(new FlexMarkInvokerStatement(rows));
        limitStatement.setSecond(new FlexMarkInvokerStatement(offset));
        statement().setLimitStatement(limitStatement);
        return (Union) creatorFactory().newUnionDef(statement());
    }
}
