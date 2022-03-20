package com.moxa.dream.module.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ObjectTypeHandler extends BaseTypeHandler<Object> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, int jdbcType) throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        return rs.getObject(columnIndex);
    }

    @Override
    public int getNullType() {
        return Types.OTHER;
    }
}
