package com.dream.template.mapper;

import com.dream.system.config.Configuration;
import com.dream.system.core.session.Session;
import com.dream.system.table.TableInfo;
import com.dream.util.common.NonCollection;

import java.util.Collection;

public class SelectByIdMapper extends SelectMapper {
    public SelectByIdMapper(Session session) {
        super(session);
    }

    @Override
    protected String getOther(Configuration configuration, TableInfo tableInfo, Class<?> type, Object arg) {
        return super.getIdWhere(tableInfo);
    }

    @Override
    protected Class<? extends Collection> getRowType() {
        return NonCollection.class;
    }

}
