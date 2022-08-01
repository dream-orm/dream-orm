package com.moxa.dream.system.typehandler.handler;

import java.sql.*;

public class SqlDateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Date parameter, int jdbcType) throws SQLException {
        if (Types.DATE == jdbcType) {
            ps.setDate(i, parameter);
        } else {
            ps.setTimestamp(i, new Timestamp(parameter.getTime()));
        }
    }

    @Override
    public Date getResult(ResultSet rs, int i, int jdbcType) throws SQLException {
        if (Types.DATE == jdbcType) {
            return rs.getDate(i);
        } else {
            return new Date(rs.getTimestamp(i).getTime());

        }
    }

    @Override
    public int getNullType() {
        return Types.DATE;
    }
}
