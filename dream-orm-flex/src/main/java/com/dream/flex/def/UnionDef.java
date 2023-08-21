package com.dream.flex.def;

import com.dream.antlr.smt.UnionStatement;

public interface UnionDef<
        ForUpdate extends ForUpdateDef<Query>,
        Query extends QueryDef>
        extends ForUpdateDef<Query> {

    default ForUpdate union(QueryDef queryDef) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(false);
        unionStatement.setStatement(queryDef.statement());
        statement().setUnionStatement(unionStatement);
        return (ForUpdate) creatorFactory().newForUpdateDef(statement());
    }

    default ForUpdate unionAll(QueryDef queryDef) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(true);
        unionStatement.setStatement(queryDef.statement());
        statement().setUnionStatement(unionStatement);
        return (ForUpdate) creatorFactory().newForUpdateDef(statement());
    }
}
