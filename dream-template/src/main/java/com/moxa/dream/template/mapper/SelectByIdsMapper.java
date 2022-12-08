package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.attach.AttachMent;

import java.util.Collection;
import java.util.List;

public class SelectByIdsMapper extends SelectMapper {

    public SelectByIdsMapper(Session session, AttachMent attachMent) {
        super(session, attachMent);
    }

    @Override
    protected String getOther(Configuration configuration, TableInfo tableInfo, Class<?> type, Object arg) {
        return super.getIdsWhere(tableInfo);
    }

    @Override
    protected Class<? extends Collection> getRowType() {
        return List.class;
    }

}
