package com.moxa.dream.module.type.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface TypeHandler<T> {

    void setParam(PreparedStatement ps, int i, T parameter, int jdbcType) throws SQLException;

    T getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException;
}
