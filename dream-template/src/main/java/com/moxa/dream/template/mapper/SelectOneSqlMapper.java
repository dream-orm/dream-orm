package com.moxa.dream.template.mapper;

import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.util.common.NonCollection;

import java.util.Collection;

public class SelectOneSqlMapper extends SelectListSqlMapper {
    public SelectOneSqlMapper(Session session) {
        super(session);
    }

    @Override
    protected Class<? extends Collection> getRowType() {
        return NonCollection.class;
    }
}
