package com.dream.system.typehandler.handler;

import java.sql.*;

public class TimeStampTypeHandler extends BaseTypeHandler<Timestamp> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Timestamp parameter, int jdbcType) throws SQLException {
        ps.setTimestamp(index, parameter);
    }

    @Override
    public Timestamp getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        return rs.getTimestamp(index);
    }

    @Override
    public Timestamp getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        return rs.getTimestamp(column);
    }

    @Override
    public int getNullType() {
        return Types.TIMESTAMP;
    }
}
