package com.moxa.dream.system.core.statementhandler;

import com.moxa.dream.system.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface StatementHandler {

    ResultSet executeQuery(MappedStatement mappedStatement) throws SQLException;

    int executeUpdate(MappedStatement mappedStatement) throws SQLException;

    int[] executeBatch(List<MappedStatement> mappedStatements) throws SQLException;

    void prepare(Connection connection, MappedStatement mappedStatement) throws SQLException;

    Statement getStatement();

    void close();


}
