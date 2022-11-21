package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;

import java.util.Collection;
import java.util.List;

public class SelectByIdsMapper extends SelectMapper {

    public SelectByIdsMapper(Session session) {
        super(session);
    }

    @Override
    protected String getOther(Configuration configuration, TableInfo tableInfo, Object arg) {
        return super.getIdsWhere(tableInfo);
    }

    @Override
    protected Class<? extends Collection> getRowType() {
        return List.class;
    }

}
