package com.dream.wrap.wrapper;

import com.dream.antlr.smt.UnionStatement;

public interface UnionWrapper<
        ForUpdate extends ForUpdateWrapper<Query>,
        Query extends QueryWrapper> extends ForUpdateWrapper<Query> {

    default ForUpdate union(QueryWrapper queryWrapper) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(false);
        unionStatement.setStatement(queryWrapper.statement());
        statement().setUnionStatement(unionStatement);
        return (ForUpdate) creatorFactory().newForUpdateWrapper(statement());
    }

    default ForUpdate unionAll(QueryWrapper queryWrapper) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(true);
        unionStatement.setStatement(queryWrapper.statement());
        statement().setUnionStatement(unionStatement);
        return (ForUpdate) creatorFactory().newForUpdateWrapper(statement());
    }
}
