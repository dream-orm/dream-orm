package com.moxa.dream.module.engine.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public abstract class BaseTypeHandler<T> implements TypeHandler<T> {
    @Override
    public void setParam(PreparedStatement ps, int i, T parameter, int jdbcType) throws SQLException {
        if (parameter == null) {
            if (jdbcType == Types.NULL)
                jdbcType = getNullType();
            ps.setNull(i, jdbcType);
        } else
            setParameter(ps, i, parameter, jdbcType);
    }

    public abstract void setParameter(PreparedStatement ps, int i, T parameter, int jdbcType) throws SQLException;

    protected abstract int getNullType();
}