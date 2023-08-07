package com.dream.boot.impl;

import com.dream.template.mapper.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ServiceImpl<ListView, EditView> extends com.dream.template.service.ServiceImpl<ListView, EditView> {
    public ServiceImpl() {
        super(null);
    }

    @Autowired
    public void setTemplateMapper(TemplateMapper templateMapper) {
        this.templateMapper = templateMapper;
    }
}
