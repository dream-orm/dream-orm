package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.ForUpdateNoWaitStatement;
import com.moxa.dream.antlr.smt.ForUpdateStatement;

public interface UnionDef<T extends ForUpdateDef> extends ForUpdateDef {

    default T forUpdate() {
        ForUpdateStatement forUpdateStatement = new ForUpdateStatement();
        statement().setForUpdateStatement(forUpdateStatement);
        return (T) creatorFactory().newForUpdateDef(statement());
    }

    default T forUpdateNoWait() {
        ForUpdateStatement forUpdateStatement = new ForUpdateNoWaitStatement();
        statement().setForUpdateStatement(forUpdateStatement);
        return (T) creatorFactory().newForUpdateDef(statement());
    }
}
