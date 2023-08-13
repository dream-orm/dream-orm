package com.dream.jdbc.row;

import com.dream.system.config.Configuration;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public interface RowMapping<T> {

    default void init(ResultSetMetaData metaData, Configuration configuration) throws SQLException {

    }

    T mapTow(ResultSet resultSet) throws SQLException;

}
