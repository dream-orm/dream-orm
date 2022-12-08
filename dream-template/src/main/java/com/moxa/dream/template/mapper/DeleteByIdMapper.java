package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.attach.AttachMent;

public class DeleteByIdMapper extends DeleteMapper {
    public DeleteByIdMapper(Session session, AttachMent attachMent) {
        super(session, attachMent);
    }

    @Override
    protected String getOther(Configuration configuration, TableInfo tableInfo, Object arg) {
        return super.getIdWhere(tableInfo);
    }
}
