package com.dream.flex.def;

import com.dream.antlr.smt.ForUpdateNoWaitStatement;
import com.dream.antlr.smt.ForUpdateStatement;

public interface ForUpdateDef<Query extends QueryDef> extends QueryDef {

    default Query forUpdate() {
        ForUpdateStatement forUpdateStatement = new ForUpdateStatement();
        statement().setForUpdateStatement(forUpdateStatement);
        return (Query) creatorFactory().newQueryDef(statement());
    }

    default Query forUpdateNoWait() {
        ForUpdateStatement forUpdateStatement = new ForUpdateNoWaitStatement();
        statement().setForUpdateStatement(forUpdateStatement);
        return (Query) creatorFactory().newQueryDef(statement());
    }
}
