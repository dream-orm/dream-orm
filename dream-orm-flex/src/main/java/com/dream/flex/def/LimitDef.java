package com.dream.flex.def;

import com.dream.antlr.smt.LimitStatement;
import com.dream.flex.statement.TakeMarkInvokerStatement;

public interface LimitDef<
        Union extends UnionDef<ForUpdate, Query>,
        ForUpdate extends ForUpdateDef<Query>,
        Query extends QueryDef>
        extends UnionDef<ForUpdate, Query> {

    default Union limit(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(false);
        limitStatement.setFirst(new TakeMarkInvokerStatement(null, offset));
        limitStatement.setSecond(new TakeMarkInvokerStatement(null, rows));
        statement().setLimitStatement(limitStatement);
        return (Union) creatorFactory().newUnionDef(statement());
    }

    default Union offset(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(true);
        limitStatement.setFirst(new TakeMarkInvokerStatement(null, rows));
        limitStatement.setSecond(new TakeMarkInvokerStatement(null, offset));
        statement().setLimitStatement(limitStatement);
        return (Union) creatorFactory().newUnionDef(statement());
    }
}
