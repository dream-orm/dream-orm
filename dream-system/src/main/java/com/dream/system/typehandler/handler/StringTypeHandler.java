package com.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class StringTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setParameter(PreparedStatement ps, int index, String parameter, int jdbcType) throws SQLException {
        ps.setString(index, parameter);
    }

    @Override
    public String getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        return rs.getString(index);
    }

    @Override
    public String getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        return rs.getString(column);
    }

    @Override
    public int getNullType() {
        return Types.VARCHAR;
    }
}
