package com.moxa.dream.module.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class UtilDateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Date parameter, int jdbcType) throws SQLException {
        ps.setDate(i, new java.sql.Date(parameter.getTime()));
    }

    @Override
    public Date getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        java.sql.Date date = rs.getDate(columnIndex);
        if (date != null)
            return new Date(date.getTime());
        else
            return null;
    }

    @Override
    public int getNullType() {
        return Types.DATE;
    }
}
