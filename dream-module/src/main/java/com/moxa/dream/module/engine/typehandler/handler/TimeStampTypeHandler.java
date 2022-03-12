package com.moxa.dream.module.engine.typehandler.handler;

import java.sql.*;

public class TimeStampTypeHandler extends BaseTypeHandler<Timestamp> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Timestamp parameter, int jdbcType) throws SQLException {
        ps.setTimestamp(i, parameter);
    }

    @Override
    public Timestamp getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        return rs.getTimestamp(columnIndex);
    }

    @Override
    public int getNullType() {
        return Types.TIMESTAMP;
    }
}
