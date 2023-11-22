package com.dream.jdbc.core;

import com.dream.jdbc.row.RowMapping;
import com.dream.system.config.MappedStatement;
import com.dream.system.core.resultsethandler.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcResultSetHandler implements ResultSetHandler {
    private RowMapping rowMapping;

    public JdbcResultSetHandler(RowMapping rowMapping) {
        this.rowMapping = rowMapping;
    }

    @Override
    public Object result(ResultSet resultSet, MappedStatement mappedStatement) throws SQLException {
        rowMapping.init(resultSet.getMetaData(), mappedStatement.getConfiguration());
        List<Object> resultList = new ArrayList<>();
        while (resultSet.next()) {
            resultList.add(rowMapping.mapTow(resultSet));
        }
        return resultList;
    }
}
