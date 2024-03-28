package com.dream.lambda.wrapper;

import com.dream.antlr.smt.LimitStatement;
import com.dream.lambda.invoker.LambdaMarkInvokerStatement;

public interface LimitWrapper<Union extends UnionWrapper<ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<Query>,
        Query extends QueryWrapper> extends UnionWrapper<ForUpdate, Query> {

    default Union limit(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(false);
        limitStatement.setFirst(new LambdaMarkInvokerStatement(offset));
        limitStatement.setSecond(new LambdaMarkInvokerStatement(rows));
        statement().setLimitStatement(limitStatement);
        return (Union) creatorFactory().newUnionWrapper(statement());
    }

    default Union offset(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(true);
        limitStatement.setFirst(new LambdaMarkInvokerStatement(rows));
        limitStatement.setSecond(new LambdaMarkInvokerStatement(offset));
        statement().setLimitStatement(limitStatement);
        return (Union) creatorFactory().newUnionWrapper(statement());
    }
}
