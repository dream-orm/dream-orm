package com.moxa.dream.module.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class LongTypeHandler extends BaseTypeHandler<Long> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Long parameter, int jdbcType) throws SQLException {
        ps.setLong(i, parameter);
    }

    @Override
    public Long getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        Long result = rs.getLong(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public int getNullType() {
        return Types.BIGINT;
    }
}
