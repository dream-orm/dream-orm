package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.Session;

import java.sql.SQLException;

public interface Executor {

    Object execute(MappedStatement mappedStatement, Session session) throws SQLException;

    void commit();

    void rollback();

    void close();

    boolean isAutoCommit();
}
