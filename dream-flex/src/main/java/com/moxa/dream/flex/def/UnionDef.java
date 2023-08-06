package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.ForUpdateNoWaitStatement;
import com.moxa.dream.antlr.smt.ForUpdateStatement;

public interface UnionDef<T extends ForUpdateDef> extends ForUpdateDef {

    default T forUpdate() {
        ForUpdateStatement forUpdateStatement = new ForUpdateStatement();
        QueryCreatorFactory queryCreatorFactory = queryCreatorFactory();
        statement().setForUpdateStatement(forUpdateStatement);
        return (T) queryCreatorFactory.newForUpdateDef(statement());
    }

    default T forUpdateNoWait() {
        ForUpdateStatement forUpdateStatement = new ForUpdateNoWaitStatement();
        QueryCreatorFactory queryCreatorFactory = queryCreatorFactory();
        statement().setForUpdateStatement(forUpdateStatement);
        return (T) queryCreatorFactory.newForUpdateDef(statement());
    }
}
