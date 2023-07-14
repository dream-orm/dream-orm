package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.smt.UnionStatement;

public class LimitDef extends UnionDef {

    protected LimitDef(QueryStatement statement) {
        super(statement);
    }

    public UnionDef union(Query query) {
        return union(query, false);
    }

    public UnionDef unionAll(Query query) {
        return union(query, true);
    }

    private UnionDef union(Query query, boolean all) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(all);
        unionStatement.setStatement(query.getStatement());
        statement.setUnionStatement(unionStatement);
        return new UnionDef(statement);
    }
}
