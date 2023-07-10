package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;

public class TableDef {
    private Statement statement;

    public TableDef(String table) {
        this.statement = new SymbolStatement.SingleMarkStatement(table);
    }

    public Statement getStatement() {
        return statement;
    }
}
