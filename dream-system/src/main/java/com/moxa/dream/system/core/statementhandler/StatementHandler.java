package com.moxa.dream.system.core.statementhandler;

import com.moxa.dream.system.config.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface StatementHandler<T extends Statement> {

    ResultSet executeQuery(T statement, MappedStatement mappedStatement) throws SQLException;

    Object executeUpdate(T statement, MappedStatement mappedStatement) throws SQLException;

    Object executeInsert(T statement, MappedStatement mappedStatement) throws SQLException;

    Object executeDelete(T statement, MappedStatement mappedStatement) throws SQLException;

    Object executeBatch(T statement, List<MappedStatement> mappedStatements) throws SQLException;

    T prepare(Connection connection, MappedStatement mappedStatement) throws SQLException;
}
