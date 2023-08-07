package com.dream.template.mapper;

import com.dream.system.core.session.Session;


public class ExistByIdMapper extends SelectByIdMapper {
    public ExistByIdMapper(Session session) {
        super(session);
    }

    @Override
    protected Class<?> getColType(Class type) {
        return Integer.class;
    }

    @Override
    protected String getSelectColumn(Class<?> type) {
        return "1";
    }
}
