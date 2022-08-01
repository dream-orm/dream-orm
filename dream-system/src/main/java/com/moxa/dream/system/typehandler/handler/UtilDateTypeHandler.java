package com.moxa.dream.system.typehandler.handler;

import java.sql.*;
import java.util.Date;

public class UtilDateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Date parameter, int jdbcType) throws SQLException {
        if (Types.DATE == jdbcType) {
            ps.setDate(i, new java.sql.Date(parameter.getTime()));
        } else {
            ps.setTimestamp(i, new Timestamp(parameter.getTime()));
        }
    }

    @Override
    public Date getResult(ResultSet rs, int i, int jdbcType) throws SQLException {
        Date date;
        if (Types.DATE == jdbcType) {
            date = rs.getDate(i);
        } else {
            date = rs.getTimestamp(i);
        }
        return date;
    }

    @Override
    public int getNullType() {
        return Types.DATE;
    }
}
