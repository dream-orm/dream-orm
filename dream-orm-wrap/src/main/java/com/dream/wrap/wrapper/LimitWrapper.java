package com.dream.wrap.wrapper;

import com.dream.antlr.smt.LimitStatement;
import com.dream.struct.invoker.TakeMarkInvokerStatement;

public interface LimitWrapper<Union extends UnionWrapper<ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<Query>,
        Query extends QueryWrapper> extends UnionWrapper<ForUpdate, Query> {

    default Union limit(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(false);
        limitStatement.setFirst(new TakeMarkInvokerStatement(offset));
        limitStatement.setSecond(new TakeMarkInvokerStatement(rows));
        statement().setLimitStatement(limitStatement);
        return (Union) creatorFactory().newUnionWrapper(statement());
    }

    default Union offset(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(true);
        limitStatement.setFirst(new TakeMarkInvokerStatement(rows));
        limitStatement.setSecond(new TakeMarkInvokerStatement(offset));
        statement().setLimitStatement(limitStatement);
        return (Union) creatorFactory().newUnionWrapper(statement());
    }
}
