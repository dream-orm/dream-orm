package com.dream.jdbc.mapper;

import com.dream.jdbc.core.JdbcStatementSetter;
import com.dream.jdbc.core.StatementSetter;
import com.dream.jdbc.row.BeanPropertyRowMapping;
import com.dream.jdbc.row.ColumnMapRowMapping;
import com.dream.jdbc.row.RowMapping;
import com.dream.jdbc.row.SingleColumnRowMapping;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;

import java.util.List;
import java.util.Map;

public interface JdbcMapper {
    default int execute(String sql, Object... args) {
        return execute(sql, new JdbcStatementSetter(args));
    }

    int execute(String sql, StatementSetter statementSetter);

    default <T> List<Object> batchExecute(String sql, List<T> argList, StatementSetter statementSetter) {
        return batchExecute(sql, argList, statementSetter, 1000);
    }

    <T> List<Object> batchExecute(String sql, List<T> argList, StatementSetter statementSetter, int batchSize);

    default <T> T queryForObject(String sql, Class<T> type, Object... args) {
        return getOne(queryForList(sql, type, args));
    }

    default <T> T queryForObject(String sql, StatementSetter statementSetter, Class<T> type) {
        return getOne(queryForList(sql, statementSetter, type));
    }

    default <T> T queryForObject(String sql, RowMapping<T> rowMapping) {
        return getOne(queryForList(sql, rowMapping));
    }

    default <T> T queryForObject(String sql, RowMapping<T> rowMapping, Object... args) {
        return getOne(queryForList(sql, new JdbcStatementSetter(args), rowMapping));
    }

    default <T> T queryForObject(String sql, StatementSetter statementSetter, RowMapping<T> rowMapping) {
        return getOne(queryForList(sql, statementSetter, rowMapping));
    }

    default <T> List<T> queryForList(String sql, Class<T> type, Object... args) {
        return queryForList(sql, new JdbcStatementSetter(args), type);
    }

    default <T> List<T> queryForList(String sql, StatementSetter statementSetter, Class<T> type) {
        RowMapping rowMapping;
        if (ReflectUtil.isBaseClass(type)) {
            rowMapping = new SingleColumnRowMapping<>(type);
        } else if (Map.class.isAssignableFrom(type)) {
            rowMapping = new ColumnMapRowMapping();
        } else {
            rowMapping = new BeanPropertyRowMapping(type);
        }
        return queryForList(sql, statementSetter, rowMapping);
    }

    default <T> List<T> queryForList(String sql, RowMapping<T> rowMapping) {
        return queryForList(sql, new JdbcStatementSetter(null), rowMapping);
    }

    default <T> List<T> queryForList(String sql, RowMapping<T> rowMapping, Object... args) {
        return queryForList(sql, new JdbcStatementSetter(args), rowMapping);
    }

    <T> List<T> queryForList(String sql, StatementSetter statementSetter, RowMapping<T> rowMapping);

    default <T> T getOne(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        throw new DreamRunTimeException("数据存在多条");
    }
}
