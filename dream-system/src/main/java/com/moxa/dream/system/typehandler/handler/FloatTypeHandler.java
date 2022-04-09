package com.moxa.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class FloatTypeHandler extends BaseTypeHandler<Float> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Float parameter, int jdbcType) throws SQLException {
        ps.setFloat(i, parameter);
    }

    @Override
    public Float getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        Float result = rs.getFloat(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public int getNullType() {
        return Types.FLOAT;
    }
}
