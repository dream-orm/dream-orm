package com.dream.system.typehandler.handler;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDateTypeHandler extends BaseTypeHandler<LocalDate> {
    @Override
    public void setParameter(PreparedStatement ps, int index, LocalDate parameter, int jdbcType) throws SQLException {
        ps.setDate(index, new Date(parameter.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
    }

    @Override
    public LocalDate getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        Date date = rs.getDate(index);
        if (date == null) {
            return null;
        } else {
            return date.toLocalDate();
        }
    }

    @Override
    public LocalDate getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        Date date = rs.getDate(column);
        if (date == null) {
            return null;
        } else {
            return date.toLocalDate();
        }
    }

    @Override
    public int getNullType() {
        return Types.DATE;
    }
}
