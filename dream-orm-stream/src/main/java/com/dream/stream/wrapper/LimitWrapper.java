package com.dream.stream.wrapper;

import com.dream.antlr.smt.LimitStatement;
import com.dream.struct.invoker.TakeMarkInvokerStatement;

public interface LimitWrapper<T, Union extends UnionWrapper<T, ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<T, Query>,
        Query extends QueryWrapper<T>> extends UnionWrapper<T, ForUpdate, Query> {

    default Union limit(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(false);
        limitStatement.setFirst(new TakeMarkInvokerStatement(null, offset));
        limitStatement.setSecond(new TakeMarkInvokerStatement(null, rows));
        statement().setLimitStatement(limitStatement);
        return (Union) creatorFactory().newUnionWrapper(entityType(), statement());
    }

    default Union offset(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(true);
        limitStatement.setFirst(new TakeMarkInvokerStatement(null, rows));
        limitStatement.setSecond(new TakeMarkInvokerStatement(null, offset));
        statement().setLimitStatement(limitStatement);
        return (Union) creatorFactory().newUnionWrapper(entityType(), statement());
    }
}
