package com.moxa.dream.system.core.statementhandler;

import com.moxa.dream.system.config.BatchMappedStatement;
import com.moxa.dream.system.config.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface StatementHandler<T extends Statement> {

    ResultSet query(T statement, MappedStatement mappedStatement) throws SQLException;

    Object update(T statement, MappedStatement mappedStatement) throws SQLException;

    Object batch(T statement, BatchMappedStatement batchMappedStatement) throws SQLException;

    T prepare(Connection connection, MappedStatement mappedStatement) throws SQLException;
}
