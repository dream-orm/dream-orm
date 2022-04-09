package com.moxa.dream.system.typehandler.handler;

import java.sql.*;

public class TimeTypeHandler extends BaseTypeHandler<Time> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Time parameter, int jdbcType) throws SQLException {
        ps.setTime(i, parameter);
    }

    @Override
    public Time getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        return rs.getTime(columnIndex);
    }

    @Override
    public int getNullType() {
        return Types.TIME;
    }
}
