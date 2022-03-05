package com.moxa.dream.engine.statement;

import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface StatementHandler {

    ResultSet doQuery(Connection connection, MappedStatement mappedStatement) throws SQLException;

    int doUpdate(Connection connection, MappedStatement mappedStatement) throws SQLException;

    int[] flushStatement(boolean rollback) throws SQLException;

    void close() throws SQLException;

}
