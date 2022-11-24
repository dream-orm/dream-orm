package com.moxa.dream.system.core.resultsethandler;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler {
    Object result(ResultSet resultSet, MappedStatement mappedStatement, Session session) throws SQLException;
}
