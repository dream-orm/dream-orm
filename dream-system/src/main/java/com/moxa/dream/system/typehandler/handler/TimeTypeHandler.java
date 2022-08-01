package com.moxa.dream.system.typehandler.handler;

import java.sql.*;

public class TimeTypeHandler extends BaseTypeHandler<Time> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Time parameter, int jdbcType) throws SQLException {
        ps.setTime(i, parameter);
    }

    @Override
    public Time getResult(ResultSet rs, int i, int jdbcType) throws SQLException {
        return rs.getTime(i);
    }

    @Override
    public int getNullType() {
        return Types.TIME;
    }
}
