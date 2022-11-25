package com.moxa.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DoubleTypeHandler extends BaseTypeHandler<Double> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Double parameter, int jdbcType) throws SQLException {
        ps.setDouble(index, parameter);
    }

    @Override
    public Double getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        double result = rs.getDouble(index);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Double getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        double result = rs.getDouble(column);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public int getNullType() {
        return Types.DOUBLE;
    }
}
