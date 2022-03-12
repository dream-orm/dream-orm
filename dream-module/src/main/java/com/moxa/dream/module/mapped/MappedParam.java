package com.moxa.dream.module.mapped;

import com.moxa.dream.module.engine.type.handler.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MappedParam {
    private int jdbcType;
    private Object paramValue;
    private TypeHandler typeHandler;

    public MappedParam(int jdbcType, Object paramValue, TypeHandler typeHandler) {
        this.jdbcType = jdbcType;
        this.paramValue = paramValue;
        this.typeHandler = typeHandler;
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public Object getParamValue() {
        return paramValue;
    }

    public TypeHandler getTypeHandler() {
        return typeHandler;
    }

    public void setParam(PreparedStatement statement, int index) throws SQLException {
        typeHandler.setParam(statement, index, paramValue, jdbcType);
    }
}
