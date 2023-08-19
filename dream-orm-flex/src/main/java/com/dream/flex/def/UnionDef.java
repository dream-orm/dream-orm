package com.dream.flex.def;

import com.dream.antlr.smt.ForUpdateNoWaitStatement;
import com.dream.antlr.smt.ForUpdateStatement;

public interface UnionDef<ForUpdate extends ForUpdateDef> extends ForUpdateDef {

    default ForUpdate forUpdate() {
        ForUpdateStatement forUpdateStatement = new ForUpdateStatement();
        statement().setForUpdateStatement(forUpdateStatement);
        return (ForUpdate) creatorFactory().newForUpdateDef(statement());
    }

    default ForUpdate forUpdateNoWait() {
        ForUpdateStatement forUpdateStatement = new ForUpdateNoWaitStatement();
        statement().setForUpdateStatement(forUpdateStatement);
        return (ForUpdate) creatorFactory().newForUpdateDef(statement());
    }
}
