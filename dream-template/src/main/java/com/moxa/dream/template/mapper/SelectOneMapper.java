package com.moxa.dream.template.mapper;

import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.util.common.NonCollection;

import java.util.Collection;

public class SelectOneMapper extends SelectListMapper {
    public SelectOneMapper(Session session) {
        super(session);
    }

    @Override
    protected Class<? extends Collection> getRowType() {
        return NonCollection.class;
    }
}
