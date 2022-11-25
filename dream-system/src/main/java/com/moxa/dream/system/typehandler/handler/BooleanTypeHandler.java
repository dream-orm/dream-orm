package com.moxa.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Boolean parameter, int jdbcType) throws SQLException {
        ps.setBoolean(index, parameter);
    }

    @Override
    public Boolean getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        boolean result = rs.getBoolean(index);
        return !result && rs.wasNull() ? null : result;
    }

    @Override
    public Boolean getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        boolean result = rs.getBoolean(column);
        return !result && rs.wasNull() ? null : result;
    }

    @Override
    public int getNullType() {
        return Types.BOOLEAN;
    }
}
