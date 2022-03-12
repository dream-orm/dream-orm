package com.moxa.dream.module.engine.statement;

import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface StatementHandler {

    ResultSet doQuery(Connection connection, MappedStatement mappedStatement) throws SQLException;

    int doUpdate(Connection connection, MappedStatement mappedStatement) throws SQLException;

    int[] flushStatement(boolean rollback);

    Statement getStatement();

    void close();


}
