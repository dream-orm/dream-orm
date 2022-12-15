package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Page;
import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.Collection;
import java.util.List;

public interface TemplateMapper {

    <T> T selectById(Class<T> type, Object id);

    <T> List<T> selectByIds(Class<T> type, List<Object> idList);

    <T> T selectOne(Class<T> type, Object conditionObject);

    <T> List<T> selectList(Class<T> type, Object conditionObject);

    <T> Page<T> selectPage(Class<T> type, Object conditionObject, Page page);

    int updateById(Object view);

    int updateNonById(Object view);

    int insert(Object view);

    Object insertFetchKey(Object view);

    int deleteById(Class<?> type, Object id);

    int deleteByIds(Class<?> type, List<?> idList);

    boolean existById(Class<?> type, Object id);

    boolean exist(Class<?> type, Object conditionObject);

    default List<Object> batchInsert(List<?> viewList) {
        return batchInsert(viewList, 1000);
    }

    default List<Object> batchUpdateById(List<?> viewList) {
        return batchUpdateById(viewList, 1000);
    }

    List<Object> batchInsert(List<?> viewList, int batchSize);

    List<Object> batchUpdateById(List<?> viewList, int batchSize);

    default <T> T selectOne(String sql, Object param, Class<T> colType) {
        return selectOne(sql, param, colType, true);
    }

    default <T> T selectOne(String sql, Object param, Class<T> colType, boolean cache) {
        List<T> resultList = selectList(sql, param, colType, cache);
        if (resultList.isEmpty()) {
            throw new DreamRunTimeException("查询数据不存在，请检查SQL:'" + sql + "'");
        }
        if (resultList.size() > 1) {
            throw new DreamRunTimeException("查询数据存在多条，请检查SQL:'" + sql + "'");
        }
        return resultList.get(0);
    }

    default <T> List<T> selectList(String sql, Object param, Class<T> colType) {
        return selectList(sql, param, colType, true);
    }

    default <T> List<T> selectList(String sql, Object param, Class<T> colType, boolean cache) {
        return (List<T>) execute(sql, param, List.class, colType, cache);
    }

    default Integer execute(String sql) {
        return execute(sql, null);
    }

    default Integer execute(String sql, Object param) {
        return (Integer) execute(sql, param, NonCollection.class, Integer.class, false);
    }

    Object execute(String sql, Object param, Class<? extends Collection> rowType, Class<?> colType, boolean cache);


}
