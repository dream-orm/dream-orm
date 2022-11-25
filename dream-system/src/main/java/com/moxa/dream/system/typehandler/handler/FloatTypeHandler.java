package com.moxa.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class FloatTypeHandler extends BaseTypeHandler<Float> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Float parameter, int jdbcType) throws SQLException {
        ps.setFloat(index, parameter);
    }

    @Override
    public Float getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        float result = rs.getFloat(index);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Float getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        float result = rs.getFloat(column);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public int getNullType() {
        return Types.FLOAT;
    }
}
