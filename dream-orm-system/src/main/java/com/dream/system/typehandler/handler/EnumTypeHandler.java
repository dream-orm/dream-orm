package com.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class EnumTypeHandler<T extends Enum<T>> extends BaseTypeHandler<T> {
    private Class<T> enumType;

    public EnumTypeHandler(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public void setParameter(PreparedStatement ps, int index, Enum parameter, int jdbcType) throws SQLException {
        ps.setString(index, parameter.name());
    }

    @Override
    protected int getNullType() {
        return Types.VARCHAR;
    }

    @Override
    public T getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        String result = rs.getString(index);
        if (result != null && !result.isEmpty()) {
            return Enum.valueOf(enumType, result);
        }
        return null;
    }

    @Override
    public T getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        String result = rs.getString(column);
        if (result != null && !result.isEmpty()) {
            return Enum.valueOf(enumType, result);
        }
        return null;
    }
}
