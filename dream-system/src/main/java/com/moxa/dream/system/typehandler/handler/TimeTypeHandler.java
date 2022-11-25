package com.moxa.dream.system.typehandler.handler;

import java.sql.*;

public class TimeTypeHandler extends BaseTypeHandler<Time> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Time parameter, int jdbcType) throws SQLException {
        ps.setTime(index, parameter);
    }

    @Override
    public Time getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        return rs.getTime(index);
    }

    @Override
    public Time getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        return rs.getTime(column);
    }

    @Override
    public int getNullType() {
        return Types.TIME;
    }
}
