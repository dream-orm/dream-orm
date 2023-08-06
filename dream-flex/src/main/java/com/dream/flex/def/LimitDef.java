package com.dream.flex.def;

import com.dream.antlr.smt.UnionStatement;

public interface LimitDef<T extends UnionDef> extends UnionDef {

    default T union(Query query) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(false);
        unionStatement.setStatement(query.statement());
        statement().setUnionStatement(unionStatement);
        return (T) creatorFactory().newUnionDef(statement());
    }

    default T unionAll(Query query) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(true);
        unionStatement.setStatement(query.statement());
        statement().setUnionStatement(unionStatement);
        return (T) creatorFactory().newUnionDef(statement());
    }
}
