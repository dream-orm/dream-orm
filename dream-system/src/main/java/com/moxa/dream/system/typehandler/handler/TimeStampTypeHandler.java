package com.moxa.dream.system.typehandler.handler;

import java.sql.*;

public class TimeStampTypeHandler extends BaseTypeHandler<Timestamp> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Timestamp parameter, int jdbcType) throws SQLException {
        ps.setTimestamp(i, parameter);
    }

    @Override
    public Timestamp getResult(ResultSet rs, int i, int jdbcType) throws SQLException {
        return rs.getTimestamp(i);
    }

    @Override
    public int getNullType() {
        return Types.TIMESTAMP;
    }
}
