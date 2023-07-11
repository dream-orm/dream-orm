package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.Statement;

public class SortDef {
    private Statement statement;

    protected SortDef(Statement statement) {
        this.statement = statement;
    }

    public Statement getStatement() {
        return this.statement;
    }

}
