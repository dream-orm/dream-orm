package com.moxa.dream.module.engine.type.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class StringTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, int jdbcType) throws SQLException {
        ps.setString(i, parameter);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    public int getNullType() {
        return Types.VARCHAR;
    }
}
