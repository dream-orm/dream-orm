package com.moxa.dream.engine.result;

import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler {
    Object result(ResultSet resultSet, MappedStatement mappedStatement) throws SQLException;
}
