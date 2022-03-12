package com.moxa.dream.module.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ByteTypeHandler extends BaseTypeHandler<Byte> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Byte parameter, int jdbcType) throws SQLException {
        ps.setByte(i, parameter);
    }

    @Override
    public Byte getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        byte result = rs.getByte(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public int getNullType() {
        return Types.TINYINT;
    }
}