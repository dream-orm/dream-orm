package com.moxa.dream.system.core.statementhandler;

import com.moxa.dream.system.config.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface StatementHandler<T extends Statement> {

    ResultSet query(T statement, MappedStatement mappedStatement) throws SQLException;

    Object update(T statement, MappedStatement mappedStatement) throws SQLException;

    Object insert(T statement, MappedStatement mappedStatement) throws SQLException;

    Object delete(T statement, MappedStatement mappedStatement) throws SQLException;

    Object batch(T statement, List<MappedStatement> mappedStatements) throws SQLException;

    T prepare(Connection connection, MappedStatement mappedStatement) throws SQLException;
}
