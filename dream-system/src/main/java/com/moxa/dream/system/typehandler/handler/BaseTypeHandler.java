package com.moxa.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public abstract class BaseTypeHandler<T> implements TypeHandler<T> {
    @Override
    public void setParam(PreparedStatement ps, int index, T parameter, int jdbcType) throws SQLException {
        if (parameter == null) {
            if (jdbcType == Types.NULL)
                jdbcType = getNullType();
            ps.setNull(index, jdbcType);
        } else
            setParameter(ps, index, parameter, jdbcType);
    }

    public abstract void setParameter(PreparedStatement ps, int index, T parameter, int jdbcType) throws SQLException;

    protected abstract int getNullType();
}
