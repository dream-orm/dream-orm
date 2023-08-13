package com.dream.system.core.statementhandler;

import com.dream.system.config.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface StatementHandler<T extends Statement> {

    ResultSet query(T statement, MappedStatement mappedStatement) throws SQLException;

    Object update(T statement, MappedStatement mappedStatement) throws SQLException;

    Object batch(T statement, MappedStatement mappedStatement) throws SQLException;

    T prepare(Connection connection, MappedStatement mappedStatement) throws SQLException;
}
