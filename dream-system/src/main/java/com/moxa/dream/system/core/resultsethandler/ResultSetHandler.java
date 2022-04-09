package com.moxa.dream.system.core.resultsethandler;

import com.moxa.dream.system.mapped.MappedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler {
    Object result(ResultSet resultSet, MappedStatement mappedStatement) throws SQLException;
}
