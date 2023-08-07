package com.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class LongTypeHandler extends BaseTypeHandler<Long> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Long parameter, int jdbcType) throws SQLException {
        ps.setLong(index, parameter);
    }

    @Override
    public Long getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        long result = rs.getLong(index);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Long getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        long result = rs.getLong(column);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public int getNullType() {
        return Types.BIGINT;
    }
}
