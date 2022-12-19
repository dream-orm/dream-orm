package com.moxa.dream.template.service;

import com.moxa.dream.system.config.Page;

import java.util.List;

public interface IService<ListView, EditView> {
    EditView selectById(Object id);

    List<EditView> selectByIds(List<?> idList);

    EditView selectOne(Object conditionObject);

    List<ListView> selectList(Object conditionObject);

    Page<ListView> selectPage(Object conditionObject, Page page);

    int updateById(EditView view);

    int updateNonById(EditView view);

    int insert(EditView view);

    Object insertFetchKey(EditView view);

    int deleteById(Object id);

    int deleteByIds(List<?> idList);

    boolean existById(Object id);

    boolean exist(Object conditionObject);

    List<Object> batchInsert(List<EditView> viewList);

    List<Object> batchUpdateById(List<EditView> viewList);

    List<Object> batchInsert(List<EditView> viewList, int batchSize);

    List<Object> batchUpdateById(List<EditView> viewList, int batchSize);
}