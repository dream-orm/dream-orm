package com.dream.jdbc.core;

import com.dream.system.config.Configuration;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementSetter<T> {
    void setter(PreparedStatement ps, Configuration configuration,T arg) throws SQLException;
}
