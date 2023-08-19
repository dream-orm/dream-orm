package com.dream.flex.def;

import com.dream.antlr.smt.UnionStatement;

public interface LimitDef<Union extends UnionDef, ForUpdate extends ForUpdateDef> extends UnionDef<ForUpdate> {

    default Union union(Query query) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(false);
        unionStatement.setStatement(query.statement());
        statement().setUnionStatement(unionStatement);
        return (Union) creatorFactory().newUnionDef(statement());
    }

    default Union unionAll(Query query) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(true);
        unionStatement.setStatement(query.statement());
        statement().setUnionStatement(unionStatement);
        return (Union) creatorFactory().newUnionDef(statement());
    }
}
