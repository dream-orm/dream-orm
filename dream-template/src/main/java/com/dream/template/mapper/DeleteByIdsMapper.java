package com.dream.template.mapper;

import com.dream.system.config.Configuration;
import com.dream.system.core.session.Session;
import com.dream.system.table.TableInfo;


public class DeleteByIdsMapper extends DeleteMapper {
    public DeleteByIdsMapper(Session session) {
        super(session);
    }

    @Override
    protected String getOther(Configuration configuration, TableInfo tableInfo, Object arg) {
        return super.getIdsWhere(tableInfo);
    }
}
