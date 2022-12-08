package com.moxa.dream.template.mapper;

import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.template.attach.AttachMent;

public class ExistByIdMapper extends SelectByIdMapper {
    public ExistByIdMapper(Session session, AttachMent attachMent) {
        super(session, attachMent);
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
