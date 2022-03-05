package com.moxa.dream.module.type.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ShortTypeHandler extends BaseTypeHandler<Short> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Short parameter, int jdbcType) throws SQLException {
        ps.setShort(i, parameter);
    }

    @Override
    public Short getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        Short result = rs.getShort(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public int getNullType() {
        return Types.SMALLINT;
    }
}
