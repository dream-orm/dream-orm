package com.dream.solon.impl;

import com.dream.template.mapper.TemplateMapper;
import org.noear.solon.annotation.Bean;

public abstract class ServiceImpl<ListView, EditView> extends com.dream.template.service.ServiceImpl<ListView, EditView> {
    public ServiceImpl() {
        super(null);
    }

    @Bean
    public void setTemplateMapper(TemplateMapper templateMapper) {
        this.templateMapper = templateMapper;
    }
}
