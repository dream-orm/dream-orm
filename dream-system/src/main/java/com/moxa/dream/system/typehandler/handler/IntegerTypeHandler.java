package com.moxa.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class IntegerTypeHandler extends BaseTypeHandler<Integer> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Integer parameter, int jdbcType) throws SQLException {
        ps.setInt(i, parameter);
    }

    @Override
    public Integer getResult(ResultSet rs, int i, int jdbcType) throws SQLException {
        Integer result = rs.getInt(i);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public int getNullType() {
        return Types.INTEGER;
    }
}
