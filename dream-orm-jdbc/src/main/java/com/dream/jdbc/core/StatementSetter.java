package com.dream.jdbc.core;

import com.dream.system.config.MappedStatement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementSetter {
    void setter(PreparedStatement ps, MappedStatement mappedStatement) throws SQLException;
}
