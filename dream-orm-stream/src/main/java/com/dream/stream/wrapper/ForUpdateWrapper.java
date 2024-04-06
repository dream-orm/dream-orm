package com.dream.stream.wrapper;

import com.dream.antlr.smt.ForUpdateNoWaitStatement;
import com.dream.antlr.smt.ForUpdateStatement;

public interface ForUpdateWrapper<T, Query extends QueryWrapper<T>> extends QueryWrapper<T> {

    default Query forUpdate() {
        ForUpdateStatement forUpdateStatement = new ForUpdateStatement();
        statement().setForUpdateStatement(forUpdateStatement);
        return (Query) creatorFactory().newQueryWrapper(entityType(), statement());
    }

    default Query forUpdateNoWait() {
        ForUpdateStatement forUpdateStatement = new ForUpdateNoWaitStatement();
        statement().setForUpdateStatement(forUpdateStatement);
        return (Query) creatorFactory().newQueryWrapper(entityType(), statement());
    }
}
