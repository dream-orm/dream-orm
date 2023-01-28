package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.config.Page;
import com.moxa.dream.template.resulthandler.Tree;
import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public interface TemplateMapper {
    TemplateMapper methodInfo(Consumer<MethodInfo> consumer);

    TemplateMapper mappedStatement(Consumer<MappedStatement> consumer);

    <T> T selectById(Class<T> type, Object id);

    <T> List<T> selectByIds(Class<T> type, Collection<?> idList);

    <T> T selectOne(Class<T> type, Object conditionObject);

    <T> List<T> selectList(Class<T> type, Object conditionObject);

    <T extends Tree> List<T> selectTree(Class<T> type, Object conditionObject);

    <T> Page<T> selectPage(Class<T> type, Object conditionObject, Page page);

    int updateById(Object view);

    int updateNonById(Object view);

    int insert(Object view);

    Object insertFetchKey(Object view);

    int deleteById(Class<?> type, Object id);

    int deleteByIds(Class<?> type, Collection<?> idList);

    boolean existById(Class<?> type, Object id);

    boolean exist(Class<?> type, Object conditionObject);

    List<Object> batchInsert(Collection<?> viewList);

    List<Object> batchUpdateById(Collection<?> viewList);

    default <T> T selectOne(String sql, Object param, Class<T> colType) {
        List<T> resultList = selectList(sql, param, colType);
        if (resultList.isEmpty()) {
            throw new DreamRunTimeException("查询数据不存在，请检查SQL:'" + sql + "'");
        }
        if (resultList.size() > 1) {
            throw new DreamRunTimeException("查询数据存在多条，请检查SQL:'" + sql + "'");
        }
        return resultList.get(0);
    }

    default <T> List<T> selectList(String sql, Object param, Class<T> colType) {
        return (List<T>) execute(sql, param, List.class, colType);
    }

    default Integer execute(String sql) {
        return execute(sql, null);
    }

    default Integer execute(String sql, Object param) {
        return (Integer) execute(sql, param, NonCollection.class, Integer.class);
    }

    Object execute(String sql, Object param, Class<? extends Collection> rowType, Class<?> colType);


}
