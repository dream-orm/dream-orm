package com.moxa.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ByteTypeHandler extends BaseTypeHandler<Byte> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Byte parameter, int jdbcType) throws SQLException {
        ps.setByte(index, parameter);
    }

    @Override
    public Byte getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        byte result = rs.getByte(index);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Byte getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        byte result = rs.getByte(column);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public int getNullType() {
        return Types.TINYINT;
    }
}
