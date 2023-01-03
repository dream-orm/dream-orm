package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Page;
import com.moxa.dream.template.resolve.MappedResolve;
import com.moxa.dream.template.resulthandler.Tree;
import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.Collection;
import java.util.List;

public interface TemplateMapper {

    default <T> T selectById(Class<T> type, Object id) {
        return selectById(type, id, null);
    }

    default <T> List<T> selectByIds(Class<T> type, List<?> idList) {
        return selectByIds(type, idList, null);
    }

    default <T> T selectOne(Class<T> type, Object conditionObject) {
        return selectOne(type, conditionObject, null);
    }

    default <T> List<T> selectList(Class<T> type, Object conditionObject) {
        return selectList(type, conditionObject, null);
    }

    default <T extends Tree> List<T> selectTree(Class<T> type, Object conditionObject) {
        return selectTree(type, conditionObject, null);
    }

    default <T> Page<T> selectPage(Class<T> type, Object conditionObject, Page page) {
        return selectPage(type, conditionObject, page, null);
    }

    default int updateById(Object view) {
        return updateById(view, null);
    }

    default int updateNonById(Object view) {
        return updateNonById(view, null);
    }

    default int insert(Object view) {
        return insert(view, null);
    }

    default Object insertFetchKey(Object view) {
        return insertFetchKey(view, null);
    }

    default int deleteById(Class<?> type, Object id) {
        return deleteById(type, id, null);
    }

    default int deleteByIds(Class<?> type, List<?> idList) {
        return deleteByIds(type, idList, null);
    }

    default boolean existById(Class<?> type, Object id) {
        return existById(type, id, null);
    }

    default boolean exist(Class<?> type, Object conditionObject) {
        return exist(type, conditionObject, null);
    }

    default List<Object> batchInsert(List<?> viewList) {
        return batchInsert(viewList, null);
    }

    default List<Object> batchUpdateById(List<?> viewList) {
        return batchUpdateById(viewList, null);
    }

    default <T> T selectOne(String sql, Object param, Class<T> colType) {
        return selectOne(sql, param, colType, null);
    }

    default <T> List<T> selectList(String sql, Object param, Class<T> colType) {
        return selectList(sql, param, colType, null);
    }

    default Integer execute(String sql) {
        return execute(sql, null);
    }

    default Integer execute(String sql, Object param) {
        return execute(sql, param, null);
    }

    <T> T selectById(Class<T> type, Object id, MappedResolve mappedResolve);

    <T> List<T> selectByIds(Class<T> type, List<?> idList, MappedResolve mappedResolve);

    <T> T selectOne(Class<T> type, Object conditionObject, MappedResolve mappedResolve);

    <T> List<T> selectList(Class<T> type, Object conditionObject, MappedResolve mappedResolve);

    <T extends Tree> List<T> selectTree(Class<T> type, Object conditionObject, MappedResolve mappedResolve);

    <T> Page<T> selectPage(Class<T> type, Object conditionObject, Page page, MappedResolve mappedResolve);

    int updateById(Object view, MappedResolve mappedResolve);

    int updateNonById(Object view, MappedResolve mappedResolve);

    int insert(Object view, MappedResolve mappedResolve);

    Object insertFetchKey(Object view, MappedResolve mappedResolve);

    int deleteById(Class<?> type, Object id, MappedResolve mappedResolve);

    int deleteByIds(Class<?> type, List<?> idList, MappedResolve mappedResolve);

    boolean existById(Class<?> type, Object id, MappedResolve mappedResolve);

    boolean exist(Class<?> type, Object conditionObject, MappedResolve mappedResolve);

    List<Object> batchInsert(List<?> viewList, MappedResolve mappedResolve);

    List<Object> batchUpdateById(List<?> viewList, MappedResolve mappedResolve);

    default <T> T selectOne(String sql, Object param, Class<T> colType, MappedResolve mappedResolve) {
        List<T> resultList = selectList(sql, param, colType, mappedResolve);
        if (resultList.isEmpty()) {
            throw new DreamRunTimeException("查询数据不存在，请检查SQL:'" + sql + "'");
        }
        if (resultList.size() > 1) {
            throw new DreamRunTimeException("查询数据存在多条，请检查SQL:'" + sql + "'");
        }
        return resultList.get(0);
    }

    default <T> List<T> selectList(String sql, Object param, Class<T> colType, MappedResolve mappedResolve) {
        return (List<T>) execute(sql, param, List.class, colType, mappedResolve);
    }

    default Integer execute(String sql, Object param, MappedResolve mappedResolve) {
        return (Integer) execute(sql, param, NonCollection.class, Integer.class, mappedResolve);
    }

    Object execute(String sql, Object param, Class<? extends Collection> rowType, Class<?> colType, MappedResolve mappedResolve);

}
