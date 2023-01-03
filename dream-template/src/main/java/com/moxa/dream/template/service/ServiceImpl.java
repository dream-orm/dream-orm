package com.moxa.dream.template.service;

import com.moxa.dream.system.config.Page;
import com.moxa.dream.template.mapper.TemplateMapper;
import com.moxa.dream.template.resolve.MappedResolve;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class ServiceImpl<ListView, EditView> implements IService<ListView, EditView> {
    protected TemplateMapper templateMapper;
    private Class listViewType;
    private Class editViewType;

    public ServiceImpl(TemplateMapper templateMapper) {
        Type serviceType = this.getClass().getGenericSuperclass();
        if (ParameterizedType.class.isInstance(serviceType)) {
            ParameterizedType parameterizedType = (ParameterizedType) serviceType;
            Type[] types = parameterizedType.getActualTypeArguments();
            listViewType = (Class) types[0];
            editViewType = (Class) types[1];
            this.templateMapper = templateMapper;
        } else {
            throw new DreamRunTimeException(this.getClass() + "未发现范型");
        }
    }

    @Override
    public EditView selectById(Object id, MappedResolve mappedResolve) {
        return (EditView) templateMapper.selectById(editViewType, id, mappedResolve);
    }

    @Override
    public List<EditView> selectByIds(List<?> idList, MappedResolve mappedResolve) {
        return templateMapper.selectByIds(editViewType, idList, mappedResolve);
    }

    @Override
    public EditView selectOne(Object conditionObject, MappedResolve mappedResolve) {
        return (EditView) templateMapper.selectOne(editViewType, conditionObject, mappedResolve);
    }

    @Override
    public List<ListView> selectList(Object conditionObject, MappedResolve mappedResolve) {
        return templateMapper.selectList(listViewType, conditionObject, mappedResolve);
    }

    @Override
    public Page<ListView> selectPage(Object conditionObject, Page page, MappedResolve mappedResolve) {
        return templateMapper.selectPage(listViewType, conditionObject, page, mappedResolve);
    }

    @Override
    public int updateById(EditView editView, MappedResolve mappedResolve) {
        return templateMapper.updateById(editView, mappedResolve);
    }

    @Override
    public int updateNonById(EditView editView, MappedResolve mappedResolve) {
        return templateMapper.updateNonById(editView, mappedResolve);
    }

    @Override
    public int insert(EditView EditView, MappedResolve mappedResolve) {
        return templateMapper.insert(EditView, mappedResolve);
    }

    @Override
    public Object insertFetchKey(EditView EditView, MappedResolve mappedResolve) {
        return templateMapper.insertFetchKey(EditView, mappedResolve);
    }

    @Override
    public int deleteById(Object id, MappedResolve mappedResolve) {
        return templateMapper.deleteById(editViewType, id, mappedResolve);
    }

    @Override
    public int deleteByIds(List<?> idList, MappedResolve mappedResolve) {
        return templateMapper.deleteByIds(editViewType, idList, mappedResolve);
    }

    @Override
    public boolean existById(Object id, MappedResolve mappedResolve) {
        return templateMapper.existById(editViewType, id, mappedResolve);
    }

    @Override
    public boolean exist(Object conditionObject, MappedResolve mappedResolve) {
        return templateMapper.exist(editViewType, conditionObject, mappedResolve);
    }

    @Override
    public List<Object> batchInsert(List<EditView> EditViews, MappedResolve mappedResolve) {
        return templateMapper.batchInsert(EditViews, mappedResolve);
    }

    @Override
    public List<Object> batchUpdateById(List<EditView> editViews, MappedResolve mappedResolve) {
        return templateMapper.batchUpdateById(editViews, mappedResolve);
    }
}
