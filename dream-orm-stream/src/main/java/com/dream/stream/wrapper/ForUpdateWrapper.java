package com.dream.stream.wrapper;

import com.dream.antlr.smt.ForUpdateNoWaitStatement;
import com.dream.antlr.smt.ForUpdateStatement;

public interface ForUpdateWrapper<Query extends QueryWrapper> extends QueryWrapper {

    default Query forUpdate() {
        ForUpdateStatement forUpdateStatement = new ForUpdateStatement();
        statement().setForUpdateStatement(forUpdateStatement);
        return (Query) creatorFactory().newQueryWrapper(statement());
    }

    default Query forUpdateNoWait() {
        ForUpdateStatement forUpdateStatement = new ForUpdateNoWaitStatement();
        statement().setForUpdateStatement(forUpdateStatement);
        return (Query) creatorFactory().newQueryWrapper(statement());
    }
}
