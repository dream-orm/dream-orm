package com.moxa.dream.template.service;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.template.mapper.TemplateMapper;

import java.util.function.Consumer;

public class TutorServiceImpl<ListView, EditView> extends ServiceImpl<ListView, EditView> {

    public TutorServiceImpl(TemplateMapper templateMapper, Class listViewType, Class editViewType) {
        super(templateMapper, listViewType, editViewType);
    }

    @Override
    public IService<ListView, EditView> methodInfo(Consumer<MethodInfo> consumer) {
        this.templateMapper = templateMapper.methodInfo(consumer);
        return this;
    }

    @Override
    public IService<ListView, EditView> mappedStatement(Consumer<MappedStatement> consumer) {
        this.templateMapper = templateMapper.mappedStatement(consumer);
        return this;
    }
}
