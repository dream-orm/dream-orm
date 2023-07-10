package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.smt.UnionStatement;

public class LimitDef extends UnionDef {

    protected LimitDef(QueryStatement statement) {
        super(statement);
    }

    public UnionDef union(ForUpdateDef forUpdateDef) {
        return union(forUpdateDef, false);
    }

    public UnionDef unionAll(ForUpdateDef forUpdateDef) {
        return union(forUpdateDef, true);
    }

    private UnionDef union(ForUpdateDef forUpdateDef, boolean all) {
        UnionStatement unionStatement = new UnionStatement();
        unionStatement.setAll(all);
        unionStatement.setStatement(forUpdateDef.statement);
        statement.setUnionStatement(unionStatement);
        return new UnionDef(statement);
    }
}
