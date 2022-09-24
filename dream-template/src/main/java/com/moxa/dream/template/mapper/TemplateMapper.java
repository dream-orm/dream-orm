package com.moxa.dream.template.mapper;

import java.util.List;

public interface TemplateMapper {

    <T> T selectById(Class<T> type, Object id);

    <T> List<T> selectByIds(Class<T> type, List<Object> idList);

    int updateById(Object view);

    int updateNonById(Object view);

    int insert(Object view);

    int insertFetchKey(Object view);

    int[] insertBatch(List<?> viewList);

    int deleteById(Class<?> type, Object id);

    int deleteByIds(Class<?> type, List<?> idList);

    boolean existById(Class<?> type, Object id);

}
