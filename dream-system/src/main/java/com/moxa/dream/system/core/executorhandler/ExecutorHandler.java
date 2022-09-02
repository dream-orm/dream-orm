package com.moxa.dream.system.core.executorhandler;

import java.sql.Connection;
import java.sql.SQLException;

public interface ExecutorHandler {
    Object execute(Connection connection) throws SQLException;
}
