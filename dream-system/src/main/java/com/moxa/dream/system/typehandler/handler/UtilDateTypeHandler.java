package com.moxa.dream.system.typehandler.handler;

import java.sql.*;
import java.util.Date;

public class UtilDateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Date parameter, int jdbcType) throws SQLException {
        if (Types.DATE == jdbcType) {
            ps.setDate(index, new java.sql.Date(parameter.getTime()));
        } else {
            ps.setTimestamp(index, new Timestamp(parameter.getTime()));
        }
    }

    @Override
    public Date getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        Date date;
        if (Types.DATE == jdbcType) {
            date = rs.getDate(index);
        } else {
            date = rs.getTimestamp(index);
        }
        return date;
    }

    @Override
    public Date getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        Date date;
        if (Types.DATE == jdbcType) {
            date = rs.getDate(column);
        } else {
            date = rs.getTimestamp(column);
        }
        return date;
    }

    @Override
    public int getNullType() {
        return Types.DATE;
    }
}
