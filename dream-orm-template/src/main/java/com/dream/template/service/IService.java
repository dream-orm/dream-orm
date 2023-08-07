package com.dream.template.service;

import com.dream.system.config.Page;

import java.util.Collection;
import java.util.List;

/**
 * 顶级Service接口
 *
 * @param <ListView> 列表页返回数据
 * @param <EditView> 编辑页返回数据
 */
public interface IService<ListView, EditView> {

    EditView selectById(Object id);

    List<EditView> selectByIds(Collection<?> idList);

    EditView selectOne(Object conditionObject);

    List<ListView> selectList(Object conditionObject);

    Page<ListView> selectPage(Object conditionObject, Page page);

    int updateById(EditView view);

    int updateNonById(EditView view);

    int insert(EditView view);

    Object insertFetchKey(EditView view);

    int deleteById(Object id);

    int deleteByIds(Collection<?> idList);

    boolean existById(Object id);

    boolean exist(Object conditionObject);

    List<Object> batchInsert(Collection<EditView> viewList);

    List<Object> batchUpdateById(Collection<EditView> viewList);
}
