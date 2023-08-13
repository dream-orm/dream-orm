package com.dream.jdbc.row;

import com.dream.system.config.Configuration;
import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.handler.ObjectTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.util.exception.DreamRunTimeException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SingleColumnRowMapping<T> implements RowMapping<T> {
    private TypeHandler typeHandler;
    private Class<T> javaType;
    private int jdbcType;


    public SingleColumnRowMapping(Class<T> javaType) {
        this.javaType = javaType;
    }

    @Override
    public void init(ResultSetMetaData metaData, Configuration configuration) throws SQLException {
        int columnCount = metaData.getColumnCount();
        if (columnCount != 1) {
            throw new DreamRunTimeException("查询字段个数必须为一个");
        }
        this.jdbcType = metaData.getColumnType(1);
        try {
            this.typeHandler = configuration.getTypeHandlerFactory().getTypeHandler(javaType, jdbcType);
        } catch (TypeHandlerNotFoundException e) {
            typeHandler = new ObjectTypeHandler();
        }
    }

    @Override
    public T mapTow(ResultSet resultSet) throws SQLException {
        return (T) typeHandler.getResult(resultSet, 1, jdbcType);
    }
}
