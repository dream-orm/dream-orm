package com.moxa.dream.system.core.executorhandler;

import com.moxa.dream.system.mapped.MappedStatement;

import java.sql.SQLException;

public interface ExecutorHandler {

    Object execute(MappedStatement mappedStatement) throws SQLException;

}
