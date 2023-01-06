package com.moxa.dream.template.service;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.config.Page;

import java.util.List;
import java.util.function.Consumer;

public interface IService<ListView, EditView> {
    IService<ListView, EditView> methodInfo(Consumer<MethodInfo> consumer);

    IService<ListView, EditView> mappedStatement(Consumer<MappedStatement> consumer);

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
}
