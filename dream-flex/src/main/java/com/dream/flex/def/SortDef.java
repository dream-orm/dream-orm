package com.dream.flex.def;

import com.dream.antlr.smt.Statement;

public class SortDef {
    private Statement statement;

    protected SortDef(Statement statement) {
        this.statement = statement;
    }

    public Statement getStatement() {
        return this.statement;
    }

}
