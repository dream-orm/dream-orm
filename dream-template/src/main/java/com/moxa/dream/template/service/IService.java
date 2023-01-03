package com.moxa.dream.template.service;

import com.moxa.dream.system.config.Page;
import com.moxa.dream.template.resolve.MappedResolve;

import java.util.List;

public interface IService<ListView, EditView> {
    default EditView selectById(Object id) {
        return selectById(id, null);
    }

    default List<EditView> selectByIds(List<?> idList) {
        return selectByIds(idList, null);
    }

    default EditView selectOne(Object conditionObject) {
        return selectOne(conditionObject, null);
    }

    default List<ListView> selectList(Object conditionObject) {
        return selectList(conditionObject, null);
    }

    default Page<ListView> selectPage(Object conditionObject, Page page) {
        return selectPage(conditionObject, page, null);
    }

    default int updateById(EditView view) {
        return updateById(view, null);
    }

    default int updateNonById(EditView view) {
        return updateNonById(view, null);
    }

    default int insert(EditView view) {
        return insert(view, null);
    }

    default Object insertFetchKey(EditView view) {
        return insert(view, null);
    }

    default int deleteById(Object id) {
        return deleteById(id, null);
    }

    default int deleteByIds(List<?> idList) {
        return deleteByIds(idList, null);
    }

    default boolean existById(Object id) {
        return existById(id, null);
    }

    default boolean exist(Object conditionObject) {
        return exist(conditionObject, null);
    }

    default List<Object> batchInsert(List<EditView> viewList) {
        return batchInsert(viewList, null);
    }

    default List<Object> batchUpdateById(List<EditView> viewList) {
        return batchUpdateById(viewList, null);
    }

    EditView selectById(Object id, MappedResolve mappedResolve);

    List<EditView> selectByIds(List<?> idList, MappedResolve mappedResolve);

    EditView selectOne(Object conditionObject, MappedResolve mappedResolve);

    List<ListView> selectList(Object conditionObject, MappedResolve mappedResolve);

    Page<ListView> selectPage(Object conditionObject, Page page, MappedResolve mappedResolve);

    int updateById(EditView view, MappedResolve mappedResolve);

    int updateNonById(EditView view, MappedResolve mappedResolve);

    int insert(EditView view, MappedResolve mappedResolve);

    Object insertFetchKey(EditView view, MappedResolve mappedResolve);

    int deleteById(Object id, MappedResolve mappedResolve);

    int deleteByIds(List<?> idList, MappedResolve mappedResolve);

    boolean existById(Object id, MappedResolve mappedResolve);

    boolean exist(Object conditionObject, MappedResolve mappedResolve);

    List<Object> batchInsert(List<EditView> viewList, MappedResolve mappedResolve);

    List<Object> batchUpdateById(List<EditView> viewList, MappedResolve mappedResolve);
}
