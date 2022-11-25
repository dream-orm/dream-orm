package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Page;

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

    default void batchInsert(List<?> viewList) {
        batchInsert(viewList, 1000);
    }

    default void batchUpdateById(List<?> viewList) {
        batchUpdateById(viewList, 1000);
    }

    default void batchUpdateNonById(List<?> viewList) {
        batchUpdateNonById(viewList, 1000);
    }

    void batchInsert(List<?> viewList, int batchSize);

    void batchUpdateById(List<?> viewList, int batchSize);

    void batchUpdateNonById(List<?> viewList, int batchSize);

}
