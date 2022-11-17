package com.moxa.dream.sql.mapper;

import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.List;

public interface SqlMapper {
    default <T> T selectOne(String sql, Object param, Class<T> type) {
        return selectOne(sql, param, type, true);
    }

    default <T> T selectOne(String sql, Object param, Class<T> type, boolean cache) {
        return selectOne(sql, param, type, cache, 0);
    }

    default <T> T selectOne(String sql, Object param, Class<T> type, boolean cache, int timeOut) {
        List<T> resultList = selectList(sql, param, type, cache, timeOut);
        if (resultList.isEmpty()) {
            throw new DreamRunTimeException("查询数据不存在，请检查SQL:'" + sql + "'");
        }
        if (resultList.size() > 1) {
            throw new DreamRunTimeException("查询数据存在多条，请检查SQL:'" + sql + "'");
        }
        return resultList.get(0);
    }

    default <T> List<T> selectList(String sql, Object param, Class<T> type) {
        return selectList(sql, param, type, true);
    }

    default <T> List<T> selectList(String sql, Object param, Class<T> type, boolean cache) {
        return selectList(sql, param, type, cache, 0);
    }

    <T> List<T> selectList(String sql, Object param, Class<T> type, boolean cache, int timeOut);

    default Integer execute(String sql) {
        return execute(sql, null);
    }

    Integer execute(String sql, Object param);
}
