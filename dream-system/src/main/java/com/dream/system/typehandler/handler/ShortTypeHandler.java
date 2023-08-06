package com.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ShortTypeHandler extends BaseTypeHandler<Short> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Short parameter, int jdbcType) throws SQLException {
        ps.setShort(index, parameter);
    }

    @Override
    public Short getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        short result = rs.getShort(index);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Short getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        short result = rs.getShort(column);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public int getNullType() {
        return Types.SMALLINT;
    }
}
