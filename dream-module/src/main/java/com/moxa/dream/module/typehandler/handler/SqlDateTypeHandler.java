package com.moxa.dream.module.typehandler.handler;

import java.sql.*;

public class SqlDateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Date parameter, int jdbcType) throws SQLException {
        ps.setDate(i, parameter);
    }

    @Override
    public Date getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        return rs.getDate(columnIndex);
    }

    @Override
    public int getNullType() {
        return Types.DATE;
    }
}
