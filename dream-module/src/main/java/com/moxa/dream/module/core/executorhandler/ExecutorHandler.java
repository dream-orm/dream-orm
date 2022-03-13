package com.moxa.dream.module.core.executorhandler;

import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.SQLException;

public interface ExecutorHandler {

     Object execute(MappedStatement mappedStatement) throws SQLException;

}
