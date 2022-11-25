package com.moxa.dream.system.typehandler.handler;

import java.sql.*;

public class SqlDateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Date parameter, int jdbcType) throws SQLException {
        if (Types.DATE == jdbcType) {
            ps.setDate(index, parameter);
        } else {
            ps.setTimestamp(index, new Timestamp(parameter.getTime()));
        }
    }

    @Override
    public Date getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        if (Types.DATE == jdbcType) {
            return rs.getDate(index);
        } else {
            return new Date(rs.getTimestamp(index).getTime());

        }
    }

    @Override
    public Date getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        if (Types.DATE == jdbcType) {
            return rs.getDate(column);
        } else {
            return new Date(rs.getTimestamp(column).getTime());

        }
    }

    @Override
    public int getNullType() {
        return Types.DATE;
    }
}
