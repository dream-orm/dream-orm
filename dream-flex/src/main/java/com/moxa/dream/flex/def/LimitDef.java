package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.smt.UnionStatement;

public class LimitDef extends UnionDef {

    protected LimitDef(QueryStatement statement) {
        super(statement);
    }

    public UnionDef union(SqlDef sqlDef) {
        return union(sqlDef, false);
    }

    public UnionDef unionAll(SqlDef sqlDef) {
        return union(sqlDef, true);
    }

    private UnionDef union(SqlDef sqlDef, boolean all) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(all);
        unionStatement.setStatement(sqlDef.getStatement());
        statement.setUnionStatement(unionStatement);
        return new UnionDef(statement);
    }
}
