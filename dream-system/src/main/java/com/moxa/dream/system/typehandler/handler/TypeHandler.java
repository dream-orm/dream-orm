package com.moxa.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface TypeHandler<T> {

    void setParam(PreparedStatement ps, int index, T parameter, int jdbcType) throws SQLException;

    T getResult(ResultSet rs, int index, int jdbcType) throws SQLException;

    T getResult(ResultSet rs, String column, int jdbcType) throws SQLException;
}
