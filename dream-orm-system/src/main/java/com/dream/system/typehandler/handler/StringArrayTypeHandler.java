package com.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class StringArrayTypeHandler extends BaseTypeHandler<String[]> {

    @Override
    public void setParameter(PreparedStatement ps, int index, String[] parameter, int jdbcType) throws SQLException {
        ps.setString(index, String.join(",", parameter));
    }

    @Override
    public String[] getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        String result = rs.getString(index);
        return getResult(result);
    }

    @Override
    public String[] getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        String result = rs.getString(column);
        return getResult(result);
    }

    @Override
    public int getNullType() {
        return Types.VARCHAR;
    }

    protected String[] getResult(String result) {
        if (result == null) {
            return null;
        }
        return result.split(",");
    }
}
