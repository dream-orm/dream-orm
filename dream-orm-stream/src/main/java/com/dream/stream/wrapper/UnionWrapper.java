package com.dream.stream.wrapper;

import com.dream.antlr.smt.UnionStatement;

public interface UnionWrapper<T,
        ForUpdate extends ForUpdateWrapper<T, Query>,
        Query extends QueryWrapper<T>> extends ForUpdateWrapper<T, Query> {

    default ForUpdate union(QueryWrapper queryWrapper) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(false);
        unionStatement.setStatement(queryWrapper.statement());
        statement().setUnionStatement(unionStatement);
        return (ForUpdate) creatorFactory().newForUpdateWrapper(entityType(), statement());
    }

    default ForUpdate unionAll(QueryWrapper queryWrapper) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(true);
        unionStatement.setStatement(queryWrapper.statement());
        statement().setUnionStatement(unionStatement);
        return (ForUpdate) creatorFactory().newForUpdateWrapper(entityType(), statement());
    }
}
