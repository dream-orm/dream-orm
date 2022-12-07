package com.moxa.dream.system.core.listener;

import com.moxa.dream.system.config.MappedStatement;

import java.sql.SQLException;

public interface Listener {
    void before(MappedStatement mappedStatement);

    void afterReturn(Object result, MappedStatement mappedStatement);

    void exception(SQLException e, MappedStatement mappedStatement);
}
