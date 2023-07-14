package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.FromStatement;
import com.moxa.dream.antlr.smt.QueryStatement;

public class SelectDef extends AbstractQuery {
    private QueryStatement statement;

    protected SelectDef(QueryStatement statement) {
        this.statement = statement;
    }

    public FromDef from(TableDef tableDef) {
        FromStatement fromStatement = new FromStatement();
        fromStatement.setMainTable(tableDef.getStatement());
        this.statement.setFromStatement(fromStatement);
        return new FromDef(statement);
    }

    @Override
    public QueryStatement getStatement() {
        return statement;
    }
}
