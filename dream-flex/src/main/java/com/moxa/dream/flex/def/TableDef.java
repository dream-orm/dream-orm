package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.AliasStatement;
import com.moxa.dream.antlr.smt.SymbolStatement;

public class TableDef {
    private AliasStatement statement;

    public TableDef(String table) {
        this(table, table);
    }

    public TableDef(String table, String alias) {
        AliasStatement aliasStatement = new AliasStatement();
        aliasStatement.setColumn(new SymbolStatement.SingleMarkStatement(table));
        this.statement = aliasStatement;
        if (!table.equals(alias)) {
            as(alias);
        }
    }

    public AliasStatement getStatement() {
        return statement;
    }

    public TableDef as(String alias) {
        statement.setAlias(new SymbolStatement.SingleMarkStatement(alias));
        return this;
    }
}
