package com.dream.mate.share.datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Logger;

public abstract class AbstractDataSource implements DataSource {

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public void setLoginTimeout(int timeout) throws SQLException {
        throw new SQLException("setLoginTimeout");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new SQLException("getLogWriter");
    }

    @Override
    public void setLogWriter(PrintWriter pw) throws SQLException {
        throw new SQLException("setLogWriter");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return (T) this;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }

    @Override
    public Logger getParentLogger() {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

}
