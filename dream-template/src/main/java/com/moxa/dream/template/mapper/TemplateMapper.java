package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.config.Page;
import com.moxa.dream.template.resulthandler.Tree;

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
}
