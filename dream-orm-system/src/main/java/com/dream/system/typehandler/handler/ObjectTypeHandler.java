package com.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ObjectTypeHandler extends BaseTypeHandler<Object> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Object parameter, int jdbcType) throws SQLException {
        ps.setObject(index, parameter);
    }

    @Override
    public Object getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        return rs.getObject(index);
    }

    @Override
    public Object getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        return rs.getObject(column);
    }

    @Override
    public int getNullType() {
        return Types.OTHER;
    }
}
