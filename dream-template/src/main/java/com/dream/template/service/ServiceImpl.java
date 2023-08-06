package com.dream.template.service;

import com.dream.system.config.Page;
import com.dream.template.mapper.TemplateMapper;
import com.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public abstract class ServiceImpl<ListView, EditView> implements IService<ListView, EditView> {
    protected TemplateMapper templateMapper;
    private Class listViewType;
    private Class editViewType;

    protected ServiceImpl(TemplateMapper templateMapper, Class listViewType, Class editViewType) {
        this.templateMapper = templateMapper;
        this.listViewType = listViewType;
        this.editViewType = editViewType;
    }

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
    public EditView selectById(Object id) {
        return (EditView) templateMapper.selectById(editViewType, id);
    }

    @Override
    public List<EditView> selectByIds(Collection<?> idList) {
        return templateMapper.selectByIds(editViewType, idList);
    }

    @Override
    public EditView selectOne(Object conditionObject) {
        return (EditView) templateMapper.selectOne(editViewType, conditionObject);
    }

    @Override
    public List<ListView> selectList(Object conditionObject) {
        return templateMapper.selectList(listViewType, conditionObject);
    }

    @Override
    public Page<ListView> selectPage(Object conditionObject, Page page) {
        return templateMapper.selectPage(listViewType, conditionObject, page);
    }

    @Override
    public int updateById(EditView editView) {
        return templateMapper.updateById(editView);
    }

    @Override
    public int updateNonById(EditView editView) {
        return templateMapper.updateNonById(editView);
    }

    @Override
    public int insert(EditView editView) {
        return templateMapper.insert(editView);
    }

    @Override
    public Object insertFetchKey(EditView editView) {
        return templateMapper.insertFetchKey(editView);
    }

    @Override
    public int deleteById(Object id) {
        return templateMapper.deleteById(editViewType, id);
    }

    @Override
    public int deleteByIds(Collection<?> idList) {
        return templateMapper.deleteByIds(editViewType, idList);
    }

    @Override
    public boolean existById(Object id) {
        return templateMapper.existById(editViewType, id);
    }

    @Override
    public boolean exist(Object conditionObject) {
        return templateMapper.exist(editViewType, conditionObject);
    }

    @Override
    public List<Object> batchInsert(Collection<EditView> editViews) {
        return templateMapper.batchInsert(editViews);
    }

    @Override
    public List<Object> batchUpdateById(Collection<EditView> editViews) {
        return templateMapper.batchUpdateById(editViews);
    }
}
