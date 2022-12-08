package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.attach.AttachMent;
import com.moxa.dream.util.common.NonCollection;

import java.util.Collection;

public class SelectByIdMapper extends SelectMapper {
    public SelectByIdMapper(Session session, AttachMent attachMent) {
        super(session, attachMent);
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
