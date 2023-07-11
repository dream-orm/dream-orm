package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.ForUpdateNoWaitStatement;
import com.moxa.dream.antlr.smt.ForUpdateStatement;
import com.moxa.dream.antlr.smt.QueryStatement;

public class UnionDef extends ForUpdateDef {

    protected UnionDef(QueryStatement statement) {
        super(statement);
    }

    public ForUpdateDef forUpdate() {
        ForUpdateStatement forUpdateStatement = new ForUpdateStatement();
        statement.setForUpdateStatement(forUpdateStatement);
        return new ForUpdateDef(statement);
    }

    public ForUpdateDef forUpdateNoWait() {
        ForUpdateStatement forUpdateStatement = new ForUpdateNoWaitStatement();
        statement.setForUpdateStatement(forUpdateStatement);
        return new ForUpdateDef(statement);
    }
}
